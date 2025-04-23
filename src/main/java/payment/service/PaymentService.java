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
import shared.PaginationQueryParams;
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
                .flatMap(validatedDto->{
                    return paymentRepository.addPayment(PaymentMapper.toPayment(validatedDto));
                });
    }

    public Uni<List<PaymentDto>> listLowestAmount(PaginationQueryParams paginationQueryParams)
    {
        return paymentRepository.lowestAmount(paginationQueryParams);
    }

}
