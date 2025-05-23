package flight.service;

import com.mongodb.reactivestreams.client.ClientSession;
import flight.models.dto.RouteQueryParams;
import flight.exceptions.FlightException;
import flight.mappers.FlightMapper;
import flight.models.FlightEntity;
import flight.models.dto.*;
import flight.repository.FlightRepository;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import shared.GlobalHibernateValidator;
import shared.PaginationQueryParams;
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
  @Inject FlightRepository flightRepository;

  @Inject TicketService ticketService;

  @Inject GlobalHibernateValidator validator;

  public Uni<List<FlightDto>> listLowestPrice(PaginationQueryParams params) {
    return flightRepository.listLowestPrice(params);
  }

  public Uni<DeleteResult> deleteById(String id) {
    return flightRepository.deleteById(id);
  }

  public Uni<InsertResult> addFlight(CreateFlightDto flightDto) {
    return validator
        .validate(flightDto)
        .flatMap(
            validatedDto ->
                flightRepository
                    .existsByFlightNumber(validatedDto.getFlightNumber())
                    .flatMap(
                        exists -> {
                          if (exists) {
                            return Uni.createFrom()
                                .failure(new FlightException(409, "Flight number already exists"));
                          }
                          FlightEntity flightEntity = FlightMapper.toFlight(validatedDto);
                          return flightRepository
                              .add(flightEntity)
                              .flatMap(
                                  insertResult -> {
                                    List<TicketEntity> tickets =
                                        generateTicketsForFlight(flightEntity);
                                    List<Uni<InsertResult>> ticketInserts =
                                        tickets.stream()
                                            .map(
                                                ticket ->
                                                    ticketService.addTicket(
                                                        TicketMapper.toCreateTicketDto(ticket)))
                                            .toList();
                                    return Uni.combine()
                                        .all()
                                        .unis(ticketInserts)
                                        .combinedWith(results -> insertResult);
                                  });
                        }));
  }

  private List<TicketEntity> generateTicketsForFlight(FlightEntity flightEntity) {
    int capacity;
    if (flightEntity.getCapacity() == null || flightEntity.getCapacity() == 0) {
      capacity = 0;
    } else {
      capacity = flightEntity.getCapacity();
    }

    return IntStream.range(0, capacity)
        .mapToObj(
            nums ->
                TicketEntity.builder()
                    .id(flightEntity.getId())
                    .flightNumber(flightEntity.getFlightNumber())
                    .price(flightEntity.getPrice())
                    .build())
        .toList();
  }

  public Uni<TicketEntity> getTicketIfAvailable(String flightNumber) {
    return flightRepository
        .getCapacityAndBookedStatusByFlightNumber(flightNumber)
        .flatMap(
            flights -> {
              BookStatusFlightDto flight = flights.getFirst();
              if (flight.getCapacity() > 0 && !flight.isBooked()) {
                return flightRepository
                    .updateFlightCapacityAndBookedStatus(flightNumber)
                    .onItem()
                    .transform(
                        updateResult -> {
                          flight.setCapacity(flight.getCapacity() - 1);
                          if (flight.getCapacity() == 0) {
                            flight.setBooked(true);
                          }
                          return flight;
                        });
              } else {
                flight.setBooked(true);
                return Uni.createFrom().item(flight);
              }
            })
        .flatMap(
            flight -> {
              if (flight.isBooked()) {
                return Uni.createFrom()
                    .failure(new FlightException(400, "Flight has already been booked!"));
              }
              return ticketService.findAvailableTicketByFlightNumber(flightNumber);
            });
  }

  public Uni<UpdateResult> incrementCapacity(String flightNumber, ClientSession clientSession) {
    return flightRepository.incrementCapacity(flightNumber, clientSession);
  }

  public Uni<List<FlightDto>> getFastestRoute(RouteQueryParams routeQueryParams) {
    return flightRepository.findFastestRoute(routeQueryParams);
  }

  public Uni<List<FlightDto>> getCheapestRoute(RouteQueryParams routeQueryParams) {
    return flightRepository.findCheapestRoute(routeQueryParams);
  }

  public Uni<List<FlightDto>> getMostExpensiveRoute(RouteQueryParams routeQueryParams) {
    return flightRepository.findMostExpensiveRoute(routeQueryParams);
  }

  public Uni<List<FlightDto>> flightsWithStopsAndWaitingTimes(RouteQueryParams routeQueryParams) {
    return flightRepository.findFlightsWithStopsAndWaitingTimes(routeQueryParams);
  }

  public Uni<List<FlightDto>> trendOverTime(DateQueryParams dateQueryParams) {
    return flightRepository.trendOverTime(dateQueryParams);
  }

  public Uni<List<TwoWayFlightDto>> findTwoWayFlights(
      String departureAirportId,
      String destinationAirportId,
      String departureDateTimeStr,
      String returnDateTimeStr) {
    LocalDateTime departureDateTime = LocalDateTime.parse(departureDateTimeStr);
    LocalDateTime returnDateTime = LocalDateTime.parse(returnDateTimeStr);

    Uni<List<FlightDto>> outboundFlightsUni =
        flightRepository.findOutboundFlights(
            departureAirportId, destinationAirportId, departureDateTime);
    Uni<List<FlightDto>> returnFlightsUni =
        flightRepository.findReturnFlights(
            departureAirportId, destinationAirportId, returnDateTime);

    return Uni.combine()
        .all()
        .unis(outboundFlightsUni, returnFlightsUni)
        .combinedWith(
            (outboundFlights, returnFlights) -> {
              return outboundFlights.stream()
                  .flatMap(
                      outboundFlight ->
                          returnFlights.stream()
                              .filter(
                                  returnFlight -> isValidTwoWayFlight(outboundFlight, returnFlight))
                              .map(
                                  returnFlight ->
                                      createTwoWayFlightDto(outboundFlight, returnFlight)))
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

    Duration outboundTravelTime =
        Duration.between(outboundFlight.getDepartureTime(), outboundFlight.getArrivalTime());
    Duration returnTravelTime =
        Duration.between(returnFlight.getDepartureTime(), returnFlight.getArrivalTime());
    Duration totalTravelTime = outboundTravelTime.plus(returnTravelTime);
    twoWayFlightDto.setTotalTravelTime(totalTravelTime);

    Duration waitingTime =
        Duration.between(outboundFlight.getArrivalTime(), returnFlight.getDepartureTime());
    twoWayFlightDto.setTotalWaitingTime(waitingTime);

    return twoWayFlightDto;
  }
}
