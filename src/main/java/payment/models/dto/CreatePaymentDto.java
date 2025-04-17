package payment.models.dto;

import jakarta.validation.constraints.NotBlank;
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
    private String reservationId;
    private Date paymentDate;
    private double paymentAmount;
    private PaymentMethod paymentMethod;
    private PaymentStatus paymentStatus;
}
