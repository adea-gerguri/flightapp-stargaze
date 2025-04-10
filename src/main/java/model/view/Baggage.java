package model.view;

import io.quarkus.mongodb.MongoClientName;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import model.dto.BaggageDto;
import model.enums.BaggageType;

import java.util.UUID;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@MongoClientName("baggages")
public class Baggage {
    private UUID id;
    private UUID reservationId;
    private BaggageType baggageType;
    private double length;
    private double height;
    private double width;
    private double weight;
    private double price;

    public Baggage(BaggageDto baggageDto){
        this.setId(baggageDto.getId());
        this.setReservationId(baggageDto.getReservationId());
        this.setBaggageType(baggageDto.getBaggageType());
        this.setLength(baggageDto.getLength());
        this.setHeight(baggageDto.getHeight());
        this.setWidth(baggageDto.getWidth());
        this.setWeight(baggageDto.getWeight());
        this.setPrice(baggageDto.getPrice());
    }

}
