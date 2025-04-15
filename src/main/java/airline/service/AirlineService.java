package airline.service;

import airline.mappers.AirlineMapper;
import airline.models.AirlineEntity;
import airline.models.dto.AirlineDto;
import airline.models.dto.CreateAirlineDTO;
import airline.repository.AirlineRepository;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.BadRequestException;
import java.util.List;

import shared.mongoUtils.DeleteResult;
import shared.mongoUtils.InsertResult;

@ApplicationScoped
public class AirlineService {
  @Inject AirlineRepository airlineRepository;

  public Uni<InsertResult> addAirline(CreateAirlineDTO airlineDTO) {
    if (!airlineDTO.isValid()) {
      return Uni.createFrom().failure(new BadRequestException("Airline not valid!"));
    }
    return Uni.createFrom()
            .item(AirlineMapper.toAirlineEntity(airlineDTO))
            .flatMap(airline ->airlineRepository.addAirline(airline));
  }

  public Uni<DeleteResult> deleteAirline(String id){
    return airlineRepository.deleteAirline(id);
  }

  public Uni<List<AirlineEntity>> groupAirlinesByCountry() {
    return airlineRepository.groupAirlinesByCountry();
  }

  public Uni<List<AirlineEntity>> groupAirlinesByCity() {
    return airlineRepository.groupByCity();
  }
}
