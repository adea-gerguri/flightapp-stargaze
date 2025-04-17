package airport.service;

import airline.exceptions.AirlineException;
import airline.mappers.AirlineMapper;
import airport.exceptions.AirportException;
import airport.mappers.AirportMapper;
import airport.models.AirportEntity;
import airport.models.dto.*;
import airport.repository.AirportRepository;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.validation.ConstraintViolationException;
import jakarta.validation.Valid;
import jakarta.ws.rs.BadRequestException;
import org.bson.Document;
import org.bson.conversions.Bson;
import shared.GlobalHibernateValidator;
import shared.mongoUtils.DeleteResult;
import shared.mongoUtils.InsertResult;
import shared.mongoUtils.UpdateResult;

import java.util.List;

@ApplicationScoped
public class AirportService {
    @Inject
    AirportRepository repository;

    @Inject
    GlobalHibernateValidator validator;

    public Uni<List<AirportEntity>> listAllAirports(){
        return repository.listAll();
    }

    public Uni<InsertResult> addAirport(CreateAirportDto airportDto) {
        return validator.validate(airportDto)
                .onFailure()
                .transform(e->new AirlineException(e.getMessage(), 400))
                .flatMap(validatedDto ->{
                    return repository.add(AirportMapper.toAirport(validatedDto));
                });
    }

    public Uni<DeleteResult> deleteAirport(String id) {
         return repository.delete(id)
                 .onItem()
                .transform(deleteResult->{
                    if(deleteResult.getDeletedCount() == 0){
                        throw new AirlineException("Airline not found", 404);
                    }
                    return deleteResult;
                });
    }

    public Uni<List<AirportGroupByCountryDto>> groupAirportsByCountry(int skip, int limit, int sort) {
        return repository.groupAirportsByCountry(skip, limit, sort)
                .onFailure()
                .transform(e->new AirlineException(e.getMessage(),404));
    }

    public Uni<List<AirportGroupByCityDto>> groupAirportsByCity(int skip, int limit, int sort){
        return repository.groupAirportsByCity(skip,limit,sort)
                .onFailure()
                .transform(e->new AirlineException(e.getMessage(),404));
    }

}


