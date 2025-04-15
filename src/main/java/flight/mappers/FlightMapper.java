package flight.mappers;

import flight.models.dto.CreateFlightDto;
import flight.models.dto.FlightDto;
import flight.models.FlightEntity;

public class FlightMapper {
    public static FlightEntity toFlight(CreateFlightDto flightDto){
        if(flightDto!=null){
            FlightEntity flightEntity = new FlightEntity();
            flightEntity.setFlightNumber(flightDto.getFlightNumber());
            flightEntity.setDepartureTime(flightDto.getDepartureTime());
            flightEntity.setArrivalTime(flightDto.getArrivalTime());
            flightEntity.setBooked(flightDto.getBooked());
            flightEntity.setCapacity(flightDto.getCapacity());
            return flightEntity;
        }
        return null;
    }
    public static CreateFlightDto toFlightDto(FlightEntity flightEntity){
        if(flightEntity !=null){
            CreateFlightDto flightDto = new CreateFlightDto();
            flightDto.setFlightNumber(flightEntity.getFlightNumber());
            flightDto.setDepartureTime(flightEntity.getDepartureTime());
            flightDto.setArrivalTime(flightEntity.getArrivalTime());
            flightDto.setBooked(flightEntity.getBooked());
            flightDto.setCapacity(flightEntity.getCapacity());
            return flightDto;
        }
        return null;
    }
}
