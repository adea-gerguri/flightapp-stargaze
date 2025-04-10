package model.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import model.enums.FlightType;
import model.view.Flight;
import org.bson.codecs.pojo.annotations.BsonId;

import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class FlightDto {
    @BsonId
    private UUID id;

    @NotBlank
    private String flightNumber;

    @NotBlank
    private UUID departureAirportId;

    @NotBlank
    private UUID arrivalAirportId;

    @NotBlank
    private String departureTime;

    @NotBlank
    private String arrivalTime;

    @NotBlank
//    private FlightType flightType; //CHANGE TO FLIGHTYPE

    @NotBlank
//    private String status; //CHANGE TO STATUS

    @NotBlank
    private double price;

    @NotBlank
    private String airline;

    @NotBlank
    private Integer capacity;

    @NotBlank
    private Boolean booked;

    public FlightDto(Flight flight){
        this.setId(flight.getId());
        this.setFlightNumber(flight.getFlightNumber());
        this.setDepartureAirportId(flight.getDepartureAirportId());
        this.setArrivalAirportId(flight.getArrivalAirportId());
        this.setDepartureTime(flight.getDepartureTime());
        this.setArrivalTime(flight.getArrivalTime());
//        this.setFlightType(flight.getFlightType());
//        this.setStatus(String.valueOf(flight.getStatus()));
        this.setPrice(flight.getPrice());
        this.setAirline(flight.getAirline());
        this.setCapacity(flight.getCapacity());
        this.setBooked(flight.getBooked());
    }
}
