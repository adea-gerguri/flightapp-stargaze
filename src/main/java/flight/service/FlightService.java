package flight.service;

import airline.exceptions.AirlineException;
import airline.mappers.AirlineMapper;
import flight.exceptions.FlightException;
import flight.mappers.FlightMapper;
import flight.models.FlightEntity;
import flight.models.dto.*;
import flight.repository.FlightRepository;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.validation.ConstraintViolationException;
import jakarta.ws.rs.BadRequestException;
import org.bson.types.ObjectId;
import shared.GlobalHibernateValidator;
import shared.mongoUtils.DeleteResult;
import shared.mongoUtils.InsertResult;
import shared.mongoUtils.UpdateResult;
import ticket.mappers.TicketMapper;
import ticket.models.TicketEntity;
import ticket.service.TicketService;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@ApplicationScoped
public class FlightService {
    @Inject
    FlightRepository flightRepository;

    @Inject
    TicketService ticketService;

    @Inject
    GlobalHibernateValidator validator;

    public Uni<List<FlightDto>> listLowestPrice(int skip, int limit){
        return flightRepository.listLowestPrice(skip, limit)
                .onFailure()
                .transform(e-> {throw new FlightException(e.getMessage(),404);
                });
    }

    public Uni<DeleteResult> deleteById(String id) {
        return flightRepository.deleteById(id)
                .onItem()
                .transform(deleteResult->{
                    if(deleteResult.getDeletedCount() == 0){
                        throw new FlightException("Flight not found", 404);
                    }
                    return deleteResult;
                });
    }

    public Uni<InsertResult> addFlight(CreateFlightDto flightDto){
        return validator.validate(flightDto)
                .onFailure(ConstraintViolationException.class)
                .transform(e->new FlightException("Failed to create flight", 400))
                .flatMap(validatedDto->{
                    System.out.println(validatedDto);
                    FlightEntity flightEntity = FlightMapper.toFlight(validatedDto);
                    return flightRepository.add(flightEntity)
                            .flatMap(insertResult->{
                                List<TicketEntity> tickets= generateTicketsForFlight(flightEntity);
                                List<Uni<InsertResult>> ticketInserts = tickets.stream()
                                        .map(ticket->ticketService.addTicket(TicketMapper.toCreateTicketDto(ticket)))
                                        .toList();
                                return Uni.combine().all().unis(ticketInserts)
                                        .combinedWith(results->insertResult);
                            });
                });
    }

    private List<TicketEntity> generateTicketsForFlight(FlightEntity flightEntity){
        int capacity;
        if(flightEntity.getCapacity() == null || flightEntity.getCapacity() == 0){
            capacity =0;
        } else{
            capacity = flightEntity.getCapacity();
        }

        System.out.println(flightEntity.getPrice());
        return IntStream.range(0, capacity)
                .mapToObj(nums-> TicketEntity.builder()
                        .id(flightEntity.getId())
                        .firstName(null)
                        .lastName(null)
                        .passportNumber(null)
                        .baggageEntityList(null)
                        .price(flightEntity.getPrice())
                        .build())
                .toList();
    }


    public Uni<BookStatusFlightDto> checkAndUpdateFlightAvailability(String flightNumber) {
        return flightRepository.getCapacityAndBookedStatusByFlightNumber(flightNumber)
                .onItem().transformToUni(flight -> {
                    if (flight.getCapacity() > 0 && !flight.isBooked()) {
                        return flightRepository.updateFlightCapacityAndBookedStatus(flightNumber)
                                .onItem().transform(updateResult -> {
                                    if (updateResult.getMatchedCount() > 0) {
                                        return flight;
                                    } else {
                                        throw new FlightException("Failed to update flight capacity", 500);
                                    }
                                });
                    } else {
                        flight.setBooked(true);
                        return Uni.createFrom().item(flight);
                    }
                });
    }

    public Uni<UpdateResult> incrementCapacity(String flightNumber) {
        return flightRepository.incrementCapacity(flightNumber);
    }


    public Uni<List<FlightDto>> getFastestRoute(String departureAirportId, String destinationAirportId, String departureDate) {
        return flightRepository.findFastestRoute(destinationAirportId, departureDate, departureAirportId)
                .onFailure()
                .transform(e-> {throw new FlightException(e.getMessage(),404);
                });
    }

    public Uni<List<FlightDto>> getCheapestRoute(String departureAirportId, String destinationAirportId, String departureDate){
        return flightRepository.findCheapestRoute(destinationAirportId, departureDate, departureAirportId)
                .onFailure()
                .transform(e-> {throw new FlightException(e.getMessage(),404);
                });
    }

    public Uni<List<FlightDto>> getMostExpensiveRoute(String departureAirportId, String destinationAirportId, String departureDate){
        return flightRepository.findMostExpensiveRoute(destinationAirportId, departureDate, departureAirportId)
                .onFailure()
                .transform(e-> {throw new FlightException(e.getMessage(),404);
                });
    }

    public Uni<List<FlightDto>> getFastestStopover(String departureAirportId, String destinationAirportId, String departureDate){
        return flightRepository.findFlightsWithStopsAndWaitingTimes(departureAirportId, destinationAirportId, departureDate)
                .onFailure()
                .transform(e-> {throw new FlightException(e.getMessage(),404);
                });
    }


    public Uni<List<TwoWayFlightDto>> findTwoWayFlights(String departureAirportId, String destinationAirportId, String departureDateTimeStr, String returnDateTimeStr) {
        LocalDateTime departureDateTime = LocalDateTime.parse(departureDateTimeStr);
        LocalDateTime returnDateTime = LocalDateTime.parse(returnDateTimeStr);

        Uni<List<FlightDto>> outboundFlightsUni = flightRepository.findOutboundFlights(departureAirportId, destinationAirportId, departureDateTime);
        Uni<List<FlightDto>> returnFlightsUni = flightRepository.findReturnFlights(departureAirportId, destinationAirportId, returnDateTime);

        return Uni.combine().all().unis(outboundFlightsUni, returnFlightsUni)
                .combinedWith((outboundFlights, returnFlights) -> {
                    return outboundFlights.stream()
                            .flatMap(outboundFlight -> returnFlights.stream()
                                    .filter(returnFlight -> isValidTwoWayFlight(outboundFlight, returnFlight))
                                    .map(returnFlight -> createTwoWayFlightDto(outboundFlight, returnFlight))
                            )
                            .collect(Collectors.toList());
                });
    }

    private boolean isValidTwoWayFlight(FlightDto outboundFlight, FlightDto returnFlight) {
        LocalDateTime outboundArrivalTime = outboundFlight.getArrivalTime();
        LocalDateTime returnDepartureTime = returnFlight.getDepartureTime();
        return returnDepartureTime.isAfter(outboundArrivalTime);
    }

    private TwoWayFlightDto createTwoWayFlightDto(FlightDto outboundFlight, FlightDto returnFlight) {
        TwoWayFlightDto twoWayFlightDto = new TwoWayFlightDto();
        twoWayFlightDto.setOutboundFlight(FlightMapper.toFlightInfoDto(outboundFlight));
        twoWayFlightDto.setReturnFlight(FlightMapper.toFlightInfoDto(returnFlight));

        twoWayFlightDto.setTotalPrice(outboundFlight.getPrice() + returnFlight.getPrice());

        Duration outboundTravelTime = Duration.between(outboundFlight.getDepartureTime(), outboundFlight.getArrivalTime());
        Duration returnTravelTime = Duration.between(returnFlight.getDepartureTime(), returnFlight.getArrivalTime());
        Duration totalTravelTime = outboundTravelTime.plus(returnTravelTime);
        twoWayFlightDto.setTotalTravelTime(totalTravelTime);

        Duration waitingTime = Duration.between(outboundFlight.getArrivalTime(), returnFlight.getDepartureTime());
        twoWayFlightDto.setTotalWaitingTime(waitingTime);

        return twoWayFlightDto;
    }
}
