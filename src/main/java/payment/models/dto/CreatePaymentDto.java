package payment.models.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import payment.enums.PaymentMethod;
import payment.enums.PaymentStatus;

import java.sql.Date;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CreatePaymentDto {
    @NotNull private String reservationId;
    @NotNull private Date paymentDate;
    @NotNull private double paymentAmount;
    @NotNull private PaymentMethod paymentMethod;
    @NotNull private PaymentStatus paymentStatus;
}
