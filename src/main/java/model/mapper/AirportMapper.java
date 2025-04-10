package model.mapper;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.context.RequestScoped;
import model.dto.AirportDto;
import model.view.Airport;

@RequestScoped
public class AirportMapper {
    public Airport toAirport(AirportDto airportDto){
        if(airportDto!=null){
            Airport airport = new Airport();
            airport.setName(airportDto.getName());
            airport.setCode(airportDto.getCode());
            airport.setCountry(airportDto.getCountry());
            airport.setAirlineName(airportDto.getAirlineName());
            return airport;
        }
        return null;
    }
    public AirportDto toAirportDto(Airport airport){
        if(airport!=null){
            AirportDto airportDto = new AirportDto();
            airportDto.setName(airport.getName());
            airportDto.setCode(airport.getCode());
            airportDto.setCountry(airport.getCountry());
            airportDto.setAirlineName(airport.getAirlineName());
            return airportDto;
        }
        return null;
    }
}

