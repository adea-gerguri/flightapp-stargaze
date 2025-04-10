package resource;

import io.smallrye.mutiny.Uni;
import jakarta.annotation.security.PermitAll;
import jakarta.annotation.security.RolesAllowed;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import model.dto.TicketDto;
import model.view.Ticket;
import repository.TicketRepository;

import java.util.List;


@Path("/tickets")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class TicketResource {
    @Inject
    TicketRepository ticketRepository;

    @GET
    @PermitAll
    public Uni<List<TicketDto>> listTickets(){
        return ticketRepository.listTickets();
    }

    @POST
    @RolesAllowed("admin")
    public Uni<List<TicketDto>> addTicket(Ticket ticket){
        return ticketRepository.addTicket(ticket)
                .onItem().ignore().andSwitchTo(this::listTickets);
    }


}
