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
import shared.PaginationQueryParams;
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
    public Uni<List<BaggageWeightDto>> getBaggageGroupedByType(@BeanParam PaginationQueryParams params) {
        return baggageService.getBaggageGroupedByType(params.getSkip(), params.getLimit(), params.getSort());
    }

    @GET
    @Path("/price")
    @PermitAll
    @Produces(MediaType.APPLICATION_JSON)
    public Uni<List<BaggagePriceDto>> getBaggageSummary(@BeanParam PaginationQueryParams params) {
        return baggageService.getBaggageSummaryByReservationId(params.getSkip(), params.getLimit(), params.getSort());
    }
}
