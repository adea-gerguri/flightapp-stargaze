package airline.models.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AirlineDto {
  @NotBlank private String name;

  @NotBlank private String country;

  @NotBlank private String city;

  @NotBlank private String code;

  @NotBlank private int planeCount;


  public boolean isValid() {
    return name != null && country != null && city!=null && code != null && planeCount != 0;
  }

}
