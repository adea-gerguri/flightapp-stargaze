package flight.service;

import flight.mappers.FlightMapper;
import flight.models.dto.CreateFlightDto;
import flight.models.dto.FlightDto;
import flight.models.dto.StopoverDto;
import flight.repository.FlightRepository;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.BadRequestException;
import shared.mongoUtils.DeleteResult;
import shared.mongoUtils.InsertResult;

import java.util.List;

@ApplicationScoped
public class FlightService {
    @Inject
    FlightRepository flightRepository;


    public Uni<List<FlightDto>> listLowestPrice(int skip, int limit){
        return flightRepository.listLowestPrice(skip, limit);
    }

    public Uni<DeleteResult> deleteById(String id) {
        return flightRepository.deleteById(id);
    }


    public Uni<InsertResult> addFlight(CreateFlightDto flightDto){
        if(!flightDto.isValid()){
            return Uni.createFrom().failure(new BadRequestException("Flight not valid!"));
        }
        return Uni.createFrom()
                .item(FlightMapper.toFlight(flightDto))
                .flatMap(flight-> flightRepository.add(FlightMapper.toFlightDto(flight)));
    }

    public Uni<List<FlightDto>> getFastestRoute(String departureAirportId, String destinationAirportId, String departureDate) {
        return flightRepository.findFastestRoute(destinationAirportId, departureDate, departureAirportId);
    }

    public Uni<List<FlightDto>> getCheapestRoute(String departureAirportId, String destinationAirportId, String departureDate){
        return flightRepository.findCheapestRoute(destinationAirportId, departureDate, departureAirportId);
    }

    public Uni<List<FlightDto>> getFastestStopover(String departureAirportId, String destinationAirportId, String departureDate){
        return flightRepository.findFlightsWithStopsAndWaitingTimes(departureAirportId, destinationAirportId, departureDate);
    }


}
