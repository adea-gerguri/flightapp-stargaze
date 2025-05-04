package payment.mapper;

import payment.models.dto.CreatePaymentDto;
import payment.models.dto.PaymentDto;
import payment.models.PaymentEntity;

public class PaymentMapper{
    public static PaymentEntity toPayment(CreatePaymentDto paymentDto){
        if(paymentDto!=null){
            PaymentEntity paymentEntity = new PaymentEntity();
            paymentEntity.setPaymentDate(paymentDto.getPaymentDate());
            paymentEntity.setPaymentAmount(paymentDto.getPaymentAmount());
            paymentEntity.setPaymentMethod(paymentDto.getPaymentMethod());
            paymentEntity.setPaymentDate(paymentDto.getPaymentDate());
            paymentEntity.setReservationId(paymentDto.getReservationId());
            return paymentEntity;
        }
        return null;
    }
    public static PaymentDto toPaymentDto(PaymentEntity paymentEntity){
        if(paymentEntity !=null){
            PaymentDto paymentDto = new PaymentDto();
            paymentDto.setPaymentDate(paymentEntity.getPaymentDate());
            paymentDto.setPaymentAmount(paymentEntity.getPaymentAmount());
            paymentDto.setPaymentMethod(paymentEntity.getPaymentMethod());
            paymentDto.setReservationId(paymentEntity.getReservationId());
            return paymentDto;
        }
        return null;
    }

}
