package resource;

import io.smallrye.mutiny.Uni;
import jakarta.annotation.security.PermitAll;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import model.dto.PaymentDto;
import model.view.Payment;
import repository.PaymentRepository;

import java.util.List;

@Path("/payments")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class PaymentResource {
    @Inject
    PaymentRepository paymentRepository;

    @GET
//    @RolesAllowed("admin")
    public Uni<List<PaymentDto>> listPayments(){
        return paymentRepository.listPayments();
    }

    @POST
    @PermitAll
    public Uni<List<PaymentDto>> add(Payment payment){
        return paymentRepository.addPayment(payment)
                .onItem().ignore().andSwitchTo(this::listPayments);
    }

}
