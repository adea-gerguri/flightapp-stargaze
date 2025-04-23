package airline.models.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class CreateAirlineDTO {
  @NotNull private String name;
  @NotNull private String country;
  @NotNull private String city;
  @NotNull private String code;
  @NotNull private int planeCount;
}
