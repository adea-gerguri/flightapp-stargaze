package baggage.mappers;

import baggage.models.dto.BaggageDto;
import baggage.models.BaggageEntity;
import baggage.models.dto.CreateBaggageDto;


public class BaggageMapper {
    public static BaggageEntity toBaggageEntity(CreateBaggageDto baggageDto){
        BaggageEntity baggageEntity = new BaggageEntity();
        baggageEntity.setBaggageType(baggageDto.getBaggageType());
        baggageEntity.setPrice(baggageDto.getPrice());
        baggageEntity.setWeight(baggageDto.getWeight());
        return baggageEntity;
    }
    public static BaggageDto toBaggageDto(BaggageEntity baggageEntity){
        BaggageDto baggageDto = new BaggageDto();
        baggageDto.setBaggageType(baggageEntity.getBaggageType());
        baggageDto.setPrice(baggageEntity.getPrice());
        baggageDto.setWeight(baggageEntity.getWeight());
        return baggageDto;
    }
}
