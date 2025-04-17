package airport.models.dto;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class UpdateAirportDto {
    private String name;
    private String code;
    private String city;
    private String country;
    private String airlineName;
    public boolean isValid() {
        return name != null && code != null && country != null && city != null;
    }

}

