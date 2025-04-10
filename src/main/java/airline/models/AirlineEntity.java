package airline.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class AirlineEntity {
  private String id;
  private String name;
  private String country;
  private String code;
  private int planeCount;
}
