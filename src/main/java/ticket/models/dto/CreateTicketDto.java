package ticket.models.dto;

import baggage.models.BaggageEntity;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.util.List;


@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
public class CreateTicketDto {
    private String userId;
    @NotNull
    @NotBlank
    private String flightNumber;
    private String reservationId;
    @NotNull
    private double price;
}
