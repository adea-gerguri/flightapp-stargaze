package payment.repository;

import airline.models.AirlineEntity;
import com.mongodb.client.model.Aggregates;
import com.mongodb.client.model.Projections;
import com.mongodb.client.model.Sorts;
import io.quarkus.mongodb.FindOptions;
import io.quarkus.mongodb.reactive.ReactiveMongoClient;
import io.quarkus.mongodb.reactive.ReactiveMongoCollection;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import org.bson.conversions.Bson;
import payment.exceptions.PaymentException;
import payment.models.PaymentEntity;
import payment.models.dto.PaymentDto;
import shared.PaginationQueryParams;
import shared.mongoUtils.InsertResult;
import shared.mongoUtils.MongoUtil;

import java.util.List;

@ApplicationScoped
public class PaymentRepository {

    @Inject
    MongoUtil mongoService;


    public Uni<List<PaymentDto>> lowestAmount(PaginationQueryParams paginationQueryParams) {
        List<Bson> pipeline = List.of(
                Aggregates.project(Projections.fields(
                        Projections.computed("id", "$_id"),
                        Projections.include("reservationId", "paymentAmount", "paymentMethod", "paymentStatus")
                ))
        );
        return MongoUtil.aggregate(getCollection(), pipeline, paginationQueryParams, PaymentDto.class);
    }

    public Uni<InsertResult> addPayment(PaymentEntity payment) {
        return MongoUtil.insertOne(getCollection(), payment);
    }

    private ReactiveMongoCollection<PaymentEntity> getCollection(){
        return mongoService.getCollection("payments", PaymentEntity.class);
    }


}

