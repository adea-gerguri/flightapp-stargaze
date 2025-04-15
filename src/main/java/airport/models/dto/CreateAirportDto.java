package airport.models.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class CreateAirportDto {
    @NotBlank private String name;

    @NotBlank private String code;

    @NotBlank private String city;

    @NotBlank private String country;


    public boolean isValid() {
        return name != null && code != null && country != null && city != null;
    }
}
