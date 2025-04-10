package resource;

import io.smallrye.mutiny.Uni;
import jakarta.annotation.security.PermitAll;
import jakarta.annotation.security.RolesAllowed;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import model.dto.ReviewDto;
import model.view.Review;
import repository.ReviewRepository;

import java.util.List;

@Path("/reviews")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class ReviewsResource {
    @Inject
    ReviewRepository reviewRepository;

    @GET
    @PermitAll
    public Uni<List<ReviewDto>> listReviews(){
        return reviewRepository.listReviews();
    }

    @POST
    @RolesAllowed("user")
    public Uni<List<ReviewDto>> addReview(Review review){
        return reviewRepository.addReview(review)
                .onItem().ignore().andSwitchTo(this::listReviews);
    }
}
