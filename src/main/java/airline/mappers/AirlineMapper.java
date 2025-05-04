package airline.mappers;

import airline.models.AirlineEntity;
import airline.models.dto.AirlineDto;
import airline.models.dto.CreateAirlineDTO;
import org.bson.types.ObjectId;

public class AirlineMapper {
  public static AirlineEntity toAirlineEntity(CreateAirlineDTO airlineDto) {
    if (airlineDto != null) {
      AirlineEntity airline = new AirlineEntity();
      airline.setName(airlineDto.getName());
      airline.setCode(airlineDto.getCode());
      airline.setCountry(airlineDto.getCountry());
      airline.setCity(airlineDto.getCity());
      airline.setPlaneCount(airlineDto.getPlaneCount());
      return airline;
    }
    return null;
  }

  public static AirlineDto toAirlineDto(AirlineEntity airline) {
    if (airline != null) {
      AirlineDto airlineDto = new AirlineDto();
      airlineDto.setName(airline.getName());
      airlineDto.setCode(airline.getCode());
      airlineDto.setCountry(airline.getCountry());
      airlineDto.setCity(airline.getCity());
      airlineDto.setPlaneCount(airline.getPlaneCount());
      return airlineDto;
    }
    return null;
  }
}
