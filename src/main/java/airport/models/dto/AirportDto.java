package airport.models.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import airport.models.AirportEntity;
import org.bson.codecs.pojo.annotations.BsonId;

import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AirportDto {
    @NotBlank
    private String name;

    @NotBlank
    private String code;

    @NotBlank
    private String city;

    @NotBlank
    private String country;
}
