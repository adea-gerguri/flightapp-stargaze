package model.mapper;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.context.RequestScoped;
import model.dto.BaggageDto;
import model.view.Baggage;
;

@RequestScoped
public class BaggageMapper  {
    public Baggage toBaggage(BaggageDto baggageDto){
        Baggage baggage = new Baggage();
        baggage.setId(baggageDto.getId());
        baggage.setBaggageType(baggageDto.getBaggageType());
        baggage.setPrice(baggageDto.getPrice());
        baggage.setHeight(baggage.getHeight());
        baggage.setWeight(baggageDto.getWeight());
        baggage.setLength(baggageDto.getLength());
        return baggage;
    }
    public BaggageDto toBaggageDto(Baggage baggage){
        BaggageDto baggageDto = new BaggageDto();
        baggageDto.setId(baggage.getId());
        baggageDto.setBaggageType(baggage.getBaggageType());
        baggageDto.setPrice(baggage.getPrice());
        baggageDto.setHeight(baggage.getHeight());
        baggageDto.setWeight(baggage.getWeight());
        baggageDto.setLength(baggage.getLength());
        baggageDto.setWidth(baggage.getWidth());
        return baggageDto;
    }
}
