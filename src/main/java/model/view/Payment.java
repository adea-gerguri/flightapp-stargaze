package model.view;

import io.quarkus.mongodb.MongoClientName;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import model.dto.PaymentDto;
import model.enums.PaymentMethod;
import model.enums.PaymentStatus;

import java.sql.Date;
import java.util.UUID;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@MongoClientName("payments")
public class Payment {
    private UUID id;
    private UUID reservationId;
    private Date paymentDate;
    private double paymentAmount;
    private PaymentMethod paymentMethod;
    private PaymentStatus paymentStatus;

    public Payment(PaymentDto paymentDto) {
        this.setId(paymentDto.getId());
        this.setReservationId(paymentDto.getReservationId());
        this.setPaymentDate(paymentDto.getPaymentDate());
        this.setPaymentAmount(paymentDto.getPaymentAmount());
        this.setPaymentMethod(paymentDto.getPaymentMethod());
        this.setPaymentStatus(paymentDto.getPaymentStatus());
    }
}
