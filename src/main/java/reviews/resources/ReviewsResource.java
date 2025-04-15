package reviews.resources;

import airline.models.AirlineEntity;
import airline.models.dto.CreateAirlineDTO;
import io.quarkus.mongodb.FindOptions;
import io.smallrye.mutiny.Uni;
import jakarta.annotation.security.PermitAll;
import jakarta.annotation.security.RolesAllowed;
import jakarta.inject.Inject;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Valid;
import jakarta.validation.Validator;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import reviews.models.dto.CreateReviewDto;
import reviews.models.dto.ReviewDto;
import reviews.models.ReviewEntity;
import reviews.repository.ReviewRepository;
import reviews.service.ReviewService;
import shared.mongoUtils.DeleteResult;
import shared.mongoUtils.InsertResult;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Path("/reviews")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class ReviewsResource {
    @Inject
    ReviewService service;


    @GET
    @PermitAll
    public Uni<List<ReviewEntity>> listReviews() {
        return service.listReviews();
    }

    @POST
    @RolesAllowed("admin")
    public Uni<InsertResult> addReview(@Valid CreateReviewDto reviewDto) {
        return service.addReview(reviewDto);
    }

    @GET
    @Path("/best/skip/{skip}/limit/{limit}")
    @PermitAll
    public Uni<List<ReviewDto>> listHighestRated(@PathParam("skip") int skip, @PathParam("limit") int limit){
        return service.listHighestRated(skip, limit);
    }

    @GET
    @Path("/worst/skip/{skip}/limit/{limit}")
    public Uni<List<ReviewDto>> listLowestRated(@PathParam("skip") int skip,@PathParam("limit") int limit){
        return service.listLowestRated(skip,limit);
    }

}
