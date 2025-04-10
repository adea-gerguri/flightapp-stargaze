package model.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import model.view.Airline;
import org.bson.types.ObjectId;

import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AirlineDto {
    @NotBlank
    private ObjectId id;

    @NotBlank
    private String name;

    @NotBlank
    private String country;

    @NotBlank
    private String code;

    @NotBlank
    private int planeCount;

    public AirlineDto(Airline airline){
        this.setId(airline.getId());
        this.setName(airline.getName());
        this.setCountry(airline.getCountry());
        this.setCode(airline.getCode());
        this.setPlaneCount(airline.getPlaneCount());
    }
}
