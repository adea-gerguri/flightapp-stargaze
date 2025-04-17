package airport.mappers;

import airport.models.dto.AirportDto;
import airport.models.AirportEntity;
import airport.models.dto.CreateAirportDto;
import airport.models.dto.UpdateAirportDto;


public class AirportMapper {
    public static AirportEntity toAirport(CreateAirportDto airportDto){
        if(airportDto!=null){
            AirportEntity airportEntity = new AirportEntity();
            airportEntity.setName(airportDto.getName());
            airportEntity.setCode(airportDto.getCode());
            airportEntity.setCountry(airportDto.getCountry());
            return airportEntity;
        }
        return null;
    }
    public static AirportDto toAirportDto(AirportEntity airportEntity){
        if(airportEntity !=null){
            AirportDto airportDto = new AirportDto();
            airportDto.setName(airportEntity.getName());
            airportDto.setCode(airportEntity.getCode());
            airportDto.setCountry(airportEntity.getCountry());

            return airportDto;
        }
        return null;
    }
    public static AirportEntity toUpdateAirport(UpdateAirportDto updateAirportDto){
        if(updateAirportDto !=null){
            AirportEntity airportEntity = new AirportEntity();
            airportEntity.setName(updateAirportDto.getName());
            airportEntity.setCode(updateAirportDto.getCode());
            airportEntity.setCountry(updateAirportDto.getCountry());
            return airportEntity;
        }
        return null;
    }
}

