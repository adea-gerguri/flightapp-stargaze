package model.mapper;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.context.RequestScoped;
import model.dto.FlightDto;
import model.view.Flight;

@RequestScoped
public class FlightMapper {
    public Flight toFlight(FlightDto flightDto){
        if(flightDto!=null){
            Flight flight = new Flight();
            flight.setId(flightDto.getId());
            flight.setFlightNumber(flightDto.getFlightNumber());
            flight.setDepartureTime(flightDto.getDepartureTime());
            flight.setArrivalTime(flightDto.getArrivalTime());
            flight.setBooked(flightDto.getBooked());
            flight.setCapacity(flightDto.getCapacity());
            flight.setArrivalAirportId(flightDto.getArrivalAirportId());
            flight.setDepartureAirportId(flightDto.getDepartureAirportId());
            return flight;
        }
        return null;
    }
    public FlightDto toFlightDto(Flight flight){
        if(flight!=null){
            FlightDto flightDto = new FlightDto();
            flightDto.setFlightNumber(flight.getFlightNumber());
            flightDto.setDepartureTime(flight.getDepartureTime());
            flightDto.setArrivalTime(flight.getArrivalTime());
            flightDto.setBooked(flight.getBooked());
            flightDto.setCapacity(flight.getCapacity());
            flightDto.setArrivalAirportId(flight.getArrivalAirportId());
            flightDto.setDepartureAirportId(flight.getDepartureAirportId());
            return flightDto;
        }
        return null;
    }
}
