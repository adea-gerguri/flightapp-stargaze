package payment.resources;

import io.smallrye.mutiny.Uni;
import jakarta.annotation.security.PermitAll;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import payment.mapper.PaymentMapper;
import payment.models.dto.CreatePaymentDto;
import payment.models.dto.PaymentDto;
import payment.models.PaymentEntity;
import payment.repository.PaymentRepository;
import payment.service.PaymentService;
import shared.PaginationQueryParams;
import shared.mongoUtils.InsertResult;

import java.util.List;

@Path("/payments")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class PaymentResource {
    @Inject
    PaymentService paymentService;


    @POST
    @PermitAll
    public Uni<InsertResult> addPayment(CreatePaymentDto paymentDto){
        return paymentService.addPayment(paymentDto);
    }

    @GET
    @PermitAll
    @Path("/lowest")
    public Uni<List<PaymentDto>> listLowestAmount(@BeanParam PaginationQueryParams params){
        return paymentService.listLowestAmount(params.getSkip(),params.getLimit());
    }


}
