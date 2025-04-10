package model.mapper;

import jakarta.enterprise.context.RequestScoped;
import model.dto.PaymentDto;
import model.view.Payment;

@RequestScoped
public class PaymentMapper{
    public Payment toPayment(PaymentDto paymentDto){
        if(paymentDto!=null){
            Payment payment = new Payment();
            payment.setId(paymentDto.getId());
            payment.setPaymentDate(paymentDto.getPaymentDate());
            payment.setPaymentAmount(paymentDto.getPaymentAmount());
            payment.setPaymentMethod(paymentDto.getPaymentMethod());
            payment.setPaymentDate(paymentDto.getPaymentDate());
            payment.setReservationId(paymentDto.getReservationId());
            return payment;
        }
        return null;
    }
    public PaymentDto toPaymentDto(Payment payment){
        if(payment!=null){
            PaymentDto paymentDto = new PaymentDto();
            paymentDto.setId(payment.getId());
            paymentDto.setPaymentDate(payment.getPaymentDate());
            paymentDto.setPaymentAmount(payment.getPaymentAmount());
            paymentDto.setPaymentMethod(payment.getPaymentMethod());
            paymentDto.setReservationId(payment.getReservationId());
            return paymentDto;
        }
        return null;
    }

}
