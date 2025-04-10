package model.view;

import io.quarkus.mongodb.MongoClientName;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import model.dto.AirportDto;

import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@MongoClientName("airports")
public class Airport {
    private UUID id;
    private String name;
    private String code;
    private String city;
    private String country;
    private String airlineName;

    public Airport(AirportDto airportDto){
        this.setId(airportDto.getId());
        this.setName(airportDto.getName());
        this.setCode(airportDto.getCode());
        this.setCity(airportDto.getCity());
        this.setCountry(airportDto.getCountry());
        this.setAirlineName(airportDto.getAirlineName());
    }
}
