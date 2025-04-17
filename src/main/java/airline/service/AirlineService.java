package airline.service;

import airline.exceptions.AirlineException;
import airline.mappers.AirlineMapper;
import airline.models.AirlineEntity;
import airline.models.dto.AirlineDto;
import airline.models.dto.AirlinesByCityDto;
import airline.models.dto.AirlinesByCountryDto;
import airline.models.dto.CreateAirlineDTO;
import airline.repository.AirlineRepository;
import airport.exceptions.AirportException;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.validation.ConstraintViolationException;
import jakarta.ws.rs.BadRequestException;
import java.util.List;

import shared.GlobalHibernateValidator;
import shared.mongoUtils.DeleteResult;
import shared.mongoUtils.InsertResult;

@ApplicationScoped
public class AirlineService {
  @Inject
  GlobalHibernateValidator validator;

  @Inject AirlineRepository airlineRepository;

  public Uni<InsertResult> addAirline(CreateAirlineDTO airlineDTO) {
      return validator.validate(airlineDTO)
              .onFailure(ConstraintViolationException.class)
              .transform(e->new AirlineException(e.getMessage(), 400))
              .flatMap(validatedDto ->{
                  return airlineRepository.addAirline(AirlineMapper.toAirlineEntity(validatedDto));
              });
  }


  public Uni<DeleteResult> deleteAirline(String id){
      return airlineRepository.deleteAirline(id)
              .onItem()
              .transform(deleteResult->{
                  if(deleteResult.getDeletedCount() == 0){
                      throw new AirlineException("Airline not found", 404);
                  }
                  return deleteResult;
              });
  }


  public Uni<List<AirlinesByCountryDto>> groupAirlinesByCountry(int skip, int limit, int sort) {
    return airlineRepository.groupAirlinesByCountry(skip, limit, sort)
            .onFailure()
            .transform(e->{throw new AirlineException(e.getMessage(), 404);});
  }

  public Uni<List<AirlinesByCityDto>> groupAirlinesByCity(int skip, int limit, int sort) {
    return airlineRepository.groupByCity(skip, limit, sort)
            .onFailure()
            .transform(e-> { throw new AirlineException(e.getMessage(), 404);});
  }
}













