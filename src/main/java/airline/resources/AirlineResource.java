package airline.resources;

import airline.models.AirlineEntity;
import airline.models.dto.AirlineDto;
import airline.models.dto.AirlinesByCityDto;
import airline.models.dto.AirlinesByCountryDto;
import airline.models.dto.CreateAirlineDTO;
import airline.service.AirlineService;
import io.smallrye.mutiny.Uni;
import jakarta.annotation.security.PermitAll;
import jakarta.annotation.security.RolesAllowed;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import java.util.List;

import shared.PaginationQueryParams;
import shared.mongoUtils.DeleteResult;
import shared.mongoUtils.InsertResult;

@Path("/airlines")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@ApplicationScoped
public class AirlineResource {
  @Inject AirlineService service;

  @POST
  @RolesAllowed("admin")
  public Uni<InsertResult> addAirline(CreateAirlineDTO createAirlineDTO) {
    return service.addAirline(createAirlineDTO);
  }

  @DELETE
  @PermitAll
  @Path("/{id}")
  public Uni<DeleteResult> deleteAirline(@PathParam("id") String id){
    return service.deleteAirline(id);
  }

  @GET
  @PermitAll
  @Path("/city")
  public Uni<List<AirlinesByCityDto>> groupByCity(@BeanParam PaginationQueryParams params) {
    return service.groupAirlinesByCity(params.getSkip(), params.getLimit(), params.getSort());
  }

  @GET
  @PermitAll
  @Path("/country")
  public Uni<List<AirlinesByCountryDto>> groupByCountry(@BeanParam PaginationQueryParams params){
    return service.groupAirlinesByCountry(params.getSkip(), params.getLimit(), params.getSort());
  }

}
