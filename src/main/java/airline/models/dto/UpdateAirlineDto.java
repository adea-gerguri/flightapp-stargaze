package airline.models.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class UpdateAirlineDto {
    @NotBlank private String name;
    @NotBlank private String country;
    @NotBlank private String city;
    @NotBlank private String code;
    @NotBlank private int planeCount;
}
