package payment.service;

import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.BadRequestException;
import payment.mapper.PaymentMapper;
import payment.models.PaymentEntity;
import payment.models.dto.CreatePaymentDto;
import payment.models.dto.PaymentDto;
import payment.repository.PaymentRepository;
import shared.mongoUtils.InsertResult;

import java.util.List;

@ApplicationScoped
public class PaymentService {
    @Inject
    PaymentRepository paymentRepository;

    public Uni<InsertResult> addPayment(CreatePaymentDto paymentDto) {
        if (!paymentDto.isValid()) {
            return Uni.createFrom().failure(new BadRequestException("Payment not made!"));
        }
        return Uni.createFrom()
                .item(PaymentMapper.toPayment(paymentDto))
                .flatMap(payment -> paymentRepository.addPayment(PaymentMapper.toPayment(paymentDto)));
    }

    public Uni<List<PaymentDto>> listLowestAmount(int skip, int limit){
        return paymentRepository.lowestAmount(skip, limit);
    }

}
