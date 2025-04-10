package model.mapper;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.context.RequestScoped;
import model.dto.AirlineDto;
import model.view.Airline;

@RequestScoped
public class AirlineMapper {
    public Airline toAirline(AirlineDto airlineDto){
        if(airlineDto!=null){
            Airline airline = new Airline();
            airline.setName(airlineDto.getName());
            airline.setCode(airlineDto.getCode());
            airline.setCountry(airlineDto.getCountry());
            airline.setPlaneCount(airlineDto.getPlaneCount());
            return airline;
        }
        return null;
    }
    public AirlineDto toAirlineDto(Airline airline){
        if(airline!=null){
            AirlineDto airlineDto = new AirlineDto();
            airlineDto.setName(airline.getName());
            airlineDto.setCode(airline.getCode());
            airlineDto.setCountry(airline.getCountry());
            airlineDto.setPlaneCount(airline.getPlaneCount());
            return airlineDto;
        }
        return null;
    }
}
