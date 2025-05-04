package baggage.service;

import airline.exceptions.AirlineException;
import airline.mappers.AirlineMapper;
import airline.models.dto.CreateAirlineDTO;
import airport.mappers.AirportMapper;
import baggage.exceptions.BaggageException;
import baggage.mappers.BaggageMapper;
import baggage.models.dto.BaggagePriceDto;
import baggage.models.dto.BaggageWeightDto;
import baggage.models.dto.CreateBaggageDto;
import baggage.repository.BaggageRepository;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.BadRequestException;
import shared.GlobalHibernateValidator;
import shared.PaginationQueryParams;
import shared.mongoUtils.InsertResult;

import java.util.List;

@ApplicationScoped
public class BaggageService {
    @Inject
    BaggageRepository baggageRepository;

    @Inject
    GlobalHibernateValidator validator;

    public Uni<InsertResult> addBaggage(CreateBaggageDto baggageDto){
        return validator.validate(baggageDto)
                .flatMap(validatedDto ->{
                    return baggageRepository.addBaggage(BaggageMapper.toBaggageEntity(validatedDto));
                });
    }

    public Uni<List<BaggageWeightDto>> getBaggageGroupedByType(PaginationQueryParams paginationQueryParams) {
        return baggageRepository.groupByBaggageType(paginationQueryParams);
    }

    public Uni<List<BaggagePriceDto>> getBaggageSummaryByReservationId(PaginationQueryParams paginationQueryParams) {
        return baggageRepository.groupByReservationIdAndTotalPrice(paginationQueryParams);
    }


}
