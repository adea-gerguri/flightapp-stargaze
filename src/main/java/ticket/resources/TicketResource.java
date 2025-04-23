package ticket.resources;

import io.smallrye.mutiny.Uni;
import jakarta.annotation.security.PermitAll;
import jakarta.annotation.security.RolesAllowed;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import shared.mongoUtils.DeleteResult;
import shared.mongoUtils.InsertResult;
import shared.mongoUtils.UpdateResult;
import ticket.models.dto.CreateTicketDto;
import ticket.models.TicketEntity;
import ticket.models.dto.TicketDto;
import ticket.service.TicketService;

import java.util.List;


@Path("/tickets")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class TicketResource {
    @Inject
    TicketService service;

    @POST
    @RolesAllowed("admin")
    public Uni<InsertResult> addTicket(CreateTicketDto createTicketDto) {
        return service.addTicket(createTicketDto);
    }

    @DELETE
    @Path("/{id}")
    @RolesAllowed("admin")
    public Uni<DeleteResult> deleteTicketById(@PathParam("id") String id){
        return service.deleteTicketById(id);
    }

    @GET
    @Path("/cheapest")
    @PermitAll
    public Uni<List<TicketDto>> getCheapestTicket() {
        return service.getCheapestTicket();
    }

    @GET
    @Path("/most-expensive")
    @PermitAll
    public Uni<List<TicketDto>> getMostExpensiveTicket() {
        return service.getMostExpensiveTicket();
    }







}
