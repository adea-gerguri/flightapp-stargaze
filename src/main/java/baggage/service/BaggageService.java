package baggage.service;

import baggage.mappers.BaggageMapper;
import baggage.models.dto.BaggagePriceDto;
import baggage.models.dto.BaggageWeightDto;
import baggage.models.dto.CreateBaggageDto;
import baggage.repository.BaggageRepository;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.BadRequestException;
import shared.mongoUtils.InsertResult;

import java.util.List;

@ApplicationScoped
public class BaggageService {
    @Inject
    BaggageRepository baggageRepository;

    public Uni<InsertResult> addBaggage(CreateBaggageDto baggageDto){
        if(!baggageDto.isValid()){
            return Uni.createFrom().failure(new BadRequestException("Baggage is not valid"));
        }
        return Uni.createFrom()
                .item(BaggageMapper.toBaggageEntity(baggageDto))
                .flatMap(baggage -> baggageRepository.addBaggage(baggage));
    }

    public Uni<List<BaggageWeightDto>> getBaggageGroupedByType() {
        return baggageRepository.groupByBaggageType();
    }

    public Uni<List<BaggagePriceDto>> getBaggageSummaryByReservationId() {
        return baggageRepository.groupByReservationIdAndTotalPrice();
    }


}
