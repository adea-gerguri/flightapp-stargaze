package payment.service;

import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.validation.ConstraintViolationException;
import jakarta.ws.rs.BadRequestException;
import payment.exceptions.PaymentException;
import payment.mapper.PaymentMapper;
import payment.models.PaymentEntity;
import payment.models.dto.CreatePaymentDto;
import payment.models.dto.PaymentDto;
import payment.repository.PaymentRepository;
import shared.GlobalHibernateValidator;
import shared.mongoUtils.InsertResult;

import java.util.List;

@ApplicationScoped
public class PaymentService {
    @Inject
    PaymentRepository paymentRepository;

    @Inject
    GlobalHibernateValidator validator;

    public Uni<InsertResult> addPayment(CreatePaymentDto paymentDto) {
        return validator.validate(paymentDto)
                .onFailure(ConstraintViolationException.class)
                .transform(e->new PaymentException(e.getMessage(),400))
                .flatMap(validatedDto->{
                    return paymentRepository.addPayment(PaymentMapper.toPayment(validatedDto));
                });
    }

    public Uni<List<PaymentDto>> listLowestAmount(int skip, int limit){
        return paymentRepository.lowestAmount(skip, limit)
                .onFailure()
                .transform(e->new PaymentException(e.getMessage(),404));
    }

}
