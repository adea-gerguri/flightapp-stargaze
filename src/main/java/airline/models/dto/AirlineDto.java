package airline.models.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AirlineDto {
  @NotNull private String name;
  @NotNull private String country;
  @NotNull private String city;
  @NotNull private String code;
  @NotNull private int planeCount;
}
