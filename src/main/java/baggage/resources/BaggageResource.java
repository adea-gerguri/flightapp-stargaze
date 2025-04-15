package baggage.resources;

import baggage.models.dto.BaggagePriceDto;
import baggage.models.dto.BaggageWeightDto;
import baggage.models.dto.CreateBaggageDto;
import baggage.service.BaggageService;
import io.smallrye.mutiny.Uni;
import jakarta.annotation.security.PermitAll;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import shared.mongoUtils.InsertResult;

import java.util.List;

@Path("/baggages")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class BaggageResource {
    @Inject
    BaggageService baggageService;


    @POST
    @PermitAll
    public Uni<InsertResult> addBaggage(CreateBaggageDto baggageDto){
        return baggageService.addBaggage(baggageDto);
    }


    @GET
    @Path("/weight")
    @PermitAll
    @Produces(MediaType.APPLICATION_JSON)
    public Uni<List<BaggageWeightDto>> getBaggageGroupedByType() {
        return baggageService.getBaggageGroupedByType();
    }

    @GET
    @Path("/price")
    @PermitAll
    @Produces(MediaType.APPLICATION_JSON)
    public Uni<List<BaggagePriceDto>> getBaggageSummary() {
        return baggageService.getBaggageSummaryByReservationId();
    }
}
