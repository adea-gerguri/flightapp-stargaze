package model.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import model.enums.PaymentMethod;
import model.enums.PaymentStatus;
import model.view.Payment;
import org.bson.codecs.pojo.annotations.BsonId;

import java.sql.Date;
import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PaymentDto {
    @NotBlank
    private UUID id;

    @NotBlank
    private UUID reservationId;

    @NotBlank
    private Date paymentDate;

    @NotBlank
    private double paymentAmount;

    @NotBlank
    private PaymentMethod paymentMethod;

    @NotBlank
    private PaymentStatus paymentStatus;

    public PaymentDto(Payment payment) {
        this.setId(payment.getId());
        this.setReservationId(payment.getReservationId());
        this.setPaymentDate(payment.getPaymentDate());
        this.setPaymentAmount(payment.getPaymentAmount());
        this.setPaymentMethod(payment.getPaymentMethod());
        this.setPaymentStatus(payment.getPaymentStatus());
    }
}
