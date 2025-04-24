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
import reviews.models.dto.HighestRatedReviewDto;
import reviews.models.dto.LowestRatedReviewDto;
import reviews.models.dto.ReviewDto;

import reviews.service.ReviewService;
import shared.PaginationQueryParams;
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


    @POST
    @RolesAllowed("admin")
    public Uni<InsertResult> addReview(CreateReviewDto reviewDto) {
        return service.addReview(reviewDto);
    }

    @GET
    @Path("/best")
    @PermitAll
    public Uni<List<HighestRatedReviewDto>> listHighestRated(@BeanParam PaginationQueryParams paginationQueryParams){
        return service.listHighestRated(paginationQueryParams);
    }

    @GET
    @Path("/worst")
    public Uni<List<LowestRatedReviewDto>> listLowestRated(@BeanParam PaginationQueryParams paginationQueryParams){
        return service.listLowestRated(paginationQueryParams);
    }

}
