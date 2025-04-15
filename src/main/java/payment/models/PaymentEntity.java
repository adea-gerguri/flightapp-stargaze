package payment.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.bson.types.ObjectId;
import payment.enums.PaymentMethod;
import payment.enums.PaymentStatus;

import java.sql.Date;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class PaymentEntity {
    private String id;
    private String reservationId;
    private Date paymentDate;
    private double paymentAmount;
    private PaymentMethod paymentMethod;
    private PaymentStatus paymentStatus;

}
