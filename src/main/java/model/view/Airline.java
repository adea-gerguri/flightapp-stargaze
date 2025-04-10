package model.view;

import io.quarkus.mongodb.MongoClientName;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import model.dto.AirlineDto;
import org.bson.types.ObjectId;

import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@MongoClientName("airlines")
public class Airline {
    private ObjectId id;
    private String name;
    private String country;
    private String code;
    private int planeCount;

    public Airline(AirlineDto airlineDto){
        this.setId(airlineDto.getId());
        this.setName(airlineDto.getName());
        this.setCountry(airlineDto.getCountry());
        this.setCode(airlineDto.getCode());
        this.setPlaneCount(airlineDto.getPlaneCount());
    }
}
