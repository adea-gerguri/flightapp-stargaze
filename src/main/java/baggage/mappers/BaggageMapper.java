package baggage.mappers;

import baggage.models.dto.BaggageDto;
import baggage.models.BaggageEntity;
import baggage.models.dto.CreateBaggageDto;


public class BaggageMapper {
    public static BaggageEntity toBaggageEntity(CreateBaggageDto baggageDto){
        BaggageEntity baggageEntity = new BaggageEntity();
        baggageEntity.setBaggageType(baggageDto.getBaggageType());
        baggageEntity.setPrice(baggageDto.getPrice());
        baggageEntity.setHeight(baggageEntity.getHeight());
        baggageEntity.setWeight(baggageDto.getWeight());
        baggageEntity.setLength(baggageDto.getLength());
        return baggageEntity;
    }
    public static BaggageDto toBaggageDto(BaggageEntity baggageEntity){
        BaggageDto baggageDto = new BaggageDto();
        baggageDto.setBaggageType(baggageEntity.getBaggageType());
        baggageDto.setPrice(baggageEntity.getPrice());
        baggageDto.setHeight(baggageEntity.getHeight());
        baggageDto.setWeight(baggageEntity.getWeight());
        baggageDto.setLength(baggageEntity.getLength());
        baggageDto.setWidth(baggageEntity.getWidth());
        return baggageDto;
    }
}
