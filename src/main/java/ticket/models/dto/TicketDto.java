package ticket.models.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import baggage.models.BaggageEntity;
import ticket.models.TicketEntity;
import org.bson.types.ObjectId;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class TicketDto {
    private String userId;
    @NotNull
    @NotBlank
    private String flightNumber;
    private String reservationId;
    @NotNull private double price;

}
