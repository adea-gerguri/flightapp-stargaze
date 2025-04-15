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

    @NotBlank
    private String reservationId;

    @NotBlank
    private Date paymentDate;

    @NotBlank
    private double paymentAmount;

    @NotBlank
    private PaymentMethod paymentMethod;

    @NotBlank
    private PaymentStatus paymentStatus;

    public boolean isValid(){
        return paymentDate != null && paymentStatus != null && paymentAmount > 0;
    }

}
