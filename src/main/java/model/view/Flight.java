package model.view;

import io.quarkus.mongodb.MongoClientName;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import model.dto.FlightDto;
import model.enums.FlightType;
import model.enums.Status;

import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@MongoClientName("flights")
public class Flight {
    private UUID id;
    private String flightNumber;
    private UUID departureAirportId;
    private UUID arrivalAirportId;
    private String departureTime;
    private String arrivalTime;
//    private FlightType flightType;
//    private Status status;
    private double price;
    private String airline;
    private Integer capacity;
    private Boolean booked;

    public Flight(FlightDto flightDto){
        this.setId(flightDto.getId());
        this.setFlightNumber(flightDto.getFlightNumber());
        this.setDepartureAirportId(flightDto.getDepartureAirportId());
        this.setArrivalAirportId(flightDto.getArrivalAirportId());
        this.setDepartureTime(flightDto.getDepartureTime());
        this.setArrivalTime(flightDto.getArrivalTime());
//        this.setFlightType(flightDto.getFlightType());
        this.setPrice(flightDto.getPrice());
        this.setAirline(flightDto.getAirline());
        this.setCapacity(flightDto.getCapacity());
        this.setBooked(flightDto.getBooked());
    }

}
