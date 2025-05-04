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
import shared.PaginationQueryParams;
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
                .flatMap(validatedDto ->{
                    return repository.add(AirportMapper.toAirport(validatedDto));
                });
    }

    public Uni<DeleteResult> deleteAirport(String id) {
         return repository.delete(id);
    }

    public Uni<List<AirportGroupByCountryDto>> groupAirportsByCountry(PaginationQueryParams paginationQueryParams) {
        return repository.groupAirportsByCountry(paginationQueryParams);
    }

    public Uni<List<AirportGroupByCityDto>> groupAirportsByCity(PaginationQueryParams paginationQueryParams) {
        return repository.groupAirportsByCity(paginationQueryParams);
    }

}


