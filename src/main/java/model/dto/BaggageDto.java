package model.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import model.enums.BaggageType;
import model.view.Baggage;
import org.bson.codecs.pojo.annotations.BsonId;

import java.util.UUID;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class BaggageDto {
    @BsonId
    private UUID id;

    @NotBlank
    private UUID reservationId;

    @NotBlank
    private BaggageType baggageType;

    @NotBlank
    private double length;

    @NotBlank
    private double height;

    @NotBlank
    private double width;

    @NotBlank
    private double weight;

    @NotBlank
    private double price;

    public BaggageDto(Baggage baggage){
        this.setBaggageType(baggage.getBaggageType());
        this.setLength(baggage.getLength());
        this.setHeight(baggage.getHeight());
        this.setWidth(baggage.getWidth());
        this.setWeight(baggage.getWeight());
        this.setPrice(baggage.getPrice());
        this.setId(baggage.getId());
    }
}
