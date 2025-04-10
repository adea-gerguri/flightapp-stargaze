package resource;

import io.smallrye.mutiny.Uni;
import jakarta.annotation.security.PermitAll;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import model.dto.BaggageDto;
import model.view.Baggage;
import repository.BaggageRepository;

import java.util.List;

@Path("/baggages")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class BaggageResource {
    @Inject
    BaggageRepository baggageRepository;

    @GET
    @PermitAll
    public Uni<List<BaggageDto>> listBaggages(){
        return baggageRepository.listBaggages();
    }

    @POST
    @PermitAll
    public Uni<List<BaggageDto>> addBaggage(Baggage baggage){
        return baggageRepository.addBaggage(baggage)
                .onItem().ignore().andSwitchTo(this::listBaggages);
    }
}
