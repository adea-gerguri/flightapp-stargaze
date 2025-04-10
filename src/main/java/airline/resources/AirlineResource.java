package airline.resources;

import airline.models.AirlineEntity;
import airline.models.dto.CreateAirlineDTO;
import airline.service.AirlineService;
import io.smallrye.mutiny.Uni;
import jakarta.annotation.security.PermitAll;
import jakarta.annotation.security.RolesAllowed;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import java.util.List;
import shared.mongoUtils.InsertResult;

@Path("/airlines")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@ApplicationScoped
public class AirlineResource {
  @Inject AirlineService service;

  @GET
  @PermitAll
  public Uni<List<AirlineEntity>> listAirlines() {
    return service.listAirlines();
  }

  @POST
  @RolesAllowed("admin")
  @Path("/admin")
  public Uni<InsertResult> addAirline(CreateAirlineDTO createAirlineDTO) {
    return service.addAirline(createAirlineDTO);
  }
}
