package airport.service;

import airport.mappers.AirportMapper;
import airport.models.AirportEntity;
import airport.models.dto.*;
import airport.repository.AirportRepository;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.BadRequestException;
import org.bson.Document;
import org.bson.conversions.Bson;
import shared.mongoUtils.DeleteResult;
import shared.mongoUtils.InsertResult;
import shared.mongoUtils.UpdateResult;

import java.util.List;

@ApplicationScoped
public class AirportService {
    @Inject
    AirportRepository repository;

    public Uni<InsertResult> addAirport(CreateAirportDto airportDto) {
        if (!airportDto.isValid()) {
            return Uni.createFrom().failure(new BadRequestException("Airport is not valid"));
        }
        return Uni.createFrom()
                .item(AirportMapper.toAirport(airportDto))
                .flatMap(airport -> repository.add(airport));
    }

    public Uni<DeleteResult> deleteAirport(String id) {
        return repository.delete(id);
    }


    public Uni<List<AirportGroupByCountryDto>> groupAirportsByCountry() {
        return repository.groupAirportsByCountry();
    }

    public Uni<List<AirportGroupByCityDto>> groupAirportsByCity(){
        return repository.groupAirportsByCity();
    }

}


