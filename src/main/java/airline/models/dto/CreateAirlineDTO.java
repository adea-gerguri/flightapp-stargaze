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
public class CreateAirlineDTO {
  @NotBlank private String name;
  @NotBlank private String country;
  @NotBlank private String code;
  @NotBlank private int planeCount;

  public boolean isValid() {
    return name != null && country != null && code != null && planeCount != 0;
  }
}
