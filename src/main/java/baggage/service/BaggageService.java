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
                .onFailure()
                .transform(e->new BaggageException(e.getMessage(), 400))
                .flatMap(validatedDto ->{
                    return baggageRepository.addBaggage(BaggageMapper.toBaggageEntity(validatedDto));
                });
    }

    public Uni<List<BaggageWeightDto>> getBaggageGroupedByType(int skip, int limit, int sort) {
        return baggageRepository.groupByBaggageType(skip, limit, sort)
                .onFailure()
                .transform(e->new BaggageException(e.getMessage(), 400));
    }

    public Uni<List<BaggagePriceDto>> getBaggageSummaryByReservationId(int skip, int limit, int sort) {
        return baggageRepository.groupByReservationIdAndTotalPrice(skip, limit, sort)
                .onFailure()
                .transform(e->new BaggageException(e.getMessage(),404));
    }


}
