package flight.service;

import airline.exceptions.AirlineException;
import airline.mappers.AirlineMapper;
import flight.exceptions.FlightException;
import flight.mappers.FlightMapper;
import flight.models.dto.CreateFlightDto;
import flight.models.dto.FlightDto;
import flight.models.dto.StopoverDto;
import flight.repository.FlightRepository;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.validation.ConstraintViolationException;
import jakarta.ws.rs.BadRequestException;
import shared.GlobalHibernateValidator;
import shared.mongoUtils.DeleteResult;
import shared.mongoUtils.InsertResult;

import java.util.List;

@ApplicationScoped
public class FlightService {
    @Inject
    FlightRepository flightRepository;

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
                .transform(e->new AirlineException(e.getMessage(), 400))
                .flatMap(validatedDto ->{
                    return flightRepository.add(FlightMapper.toFlight(validatedDto));
                });
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


}
