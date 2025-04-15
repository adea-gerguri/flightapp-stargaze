package airport.resources;

import airport.models.dto.*;
import airport.service.AirportService;
import io.smallrye.mutiny.Uni;
import jakarta.annotation.security.PermitAll;
import jakarta.annotation.security.RolesAllowed;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import airport.models.AirportEntity;
import shared.mongoUtils.DeleteResult;
import shared.mongoUtils.InsertResult;


import java.util.List;

@Path("/airports")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class AirportResource {
    @Inject
    AirportService service;


    @POST
    @RolesAllowed("admin")
    public Uni<InsertResult> addAirport(CreateAirportDto airportDto){
        return service.addAirport(airportDto);
    }


    @DELETE
    @Path("/{id}")
    @RolesAllowed("admin")
    public Uni<DeleteResult> deleteAirportById(@PathParam("id") String id){
        return service.deleteAirport(id);
    }


    @GET
    @Path("/country")
    @PermitAll
    public Uni<List<AirportGroupByCountryDto>> groupByCountry() {
        return service.groupAirportsByCountry();
    }


    @GET
    @Path("/city")
    @PermitAll
    public Uni<List<AirportGroupByCityDto>> groupByCity(){
        return service.groupAirportsByCity();
    }

}
