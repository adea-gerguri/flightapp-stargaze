package model.dto;

import io.quarkus.arc.All;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import model.view.Airport;
import org.bson.codecs.pojo.annotations.BsonId;

import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AirportDto {
    @BsonId
    private UUID id;

    @NotBlank
    private String name;

    @NotBlank
    private String code;

    @NotBlank
    private String city;

    @NotBlank
    private String country;

    @NotBlank
    private String airlineName;

    public AirportDto(Airport airport){
        this.setId(airport.getId());
        this.setName(airport.getName());
        this.setCode(airport.getCode());
        this.setCity(airport.getCity());
        this.setCountry(airport.getCountry());
        this.setAirlineName(airport.getAirlineName());
    }
}
