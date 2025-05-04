package flight.mappers;

import flight.models.dto.BookStatusFlightDto;
import flight.models.dto.CreateFlightDto;
import flight.models.FlightEntity;
import flight.models.dto.FlightDto;
import flight.models.dto.FlightInfoDto;

import java.awt.print.Book;

public class FlightMapper {
    public static FlightEntity toFlight(CreateFlightDto flightDto){
        if(flightDto!=null){
            FlightEntity flightEntity = new FlightEntity();
            flightEntity.setFlightNumber(flightDto.getFlightNumber());
            flightEntity.setDepartureTime(flightDto.getDepartureTime());
            flightEntity.setArrivalTime(flightDto.getArrivalTime());
            flightEntity.setBooked(flightDto.isBooked());
            flightEntity.setPrice(flightDto.getPrice());
            flightEntity.setCapacity(flightDto.getCapacity());
            return flightEntity;
        }
        return null;
    }
    public static BookStatusFlightDto toBookStatusFlightDto(FlightEntity flightEntity){
        if(flightEntity!=null){
            BookStatusFlightDto bookStatusFlightDto = new BookStatusFlightDto();
            flightEntity.setFlightNumber(bookStatusFlightDto.getFlightNumber());
            flightEntity.setBooked(bookStatusFlightDto.isBooked());
            flightEntity.setCapacity(bookStatusFlightDto.getCapacity());
            return bookStatusFlightDto;
        }
        return null;
    }

    public static FlightInfoDto toFlightInfoDto(FlightDto flightDto) {
        if (flightDto != null) {
            FlightInfoDto flightInfoDto = new FlightInfoDto();
            flightInfoDto.setDepartureAirportId(flightDto.getDepartureAirportId());
            flightInfoDto.setArrivalAirportId(flightDto.getArrivalAirportId());
            flightInfoDto.setDepartureTime(flightDto.getDepartureTime());
            flightInfoDto.setArrivalTime(flightDto.getArrivalTime());
            flightInfoDto.setPrice(flightDto.getPrice());
            return flightInfoDto;
        }
        return null;
    }

}
