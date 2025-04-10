package repository;

import io.quarkus.mongodb.reactive.ReactiveMongoClient;
import io.quarkus.mongodb.reactive.ReactiveMongoCollection;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import model.dto.PaymentDto;
import model.mapper.MapperService;
import model.mapper.PaymentMapper;
import model.view.Payment;
import model.enums.PaymentStatus;
import org.bson.Document;

import java.sql.Date;
import java.util.List;

@ApplicationScoped
public class PaymentRepository {

    @Inject
    ReactiveMongoClient mongoClient;

    @Inject
    PaymentMapper paymentMapper;

    @Inject
    MapperService mapper;


    public Uni<List<PaymentDto>> listPayments(){
        return getCollection().find()
                .map(document->{
                    Payment payment = new Payment();
                    payment.setPaymentDate((Date) document.getDate("paymentDate")); // fix parsing here
                    payment.setPaymentAmount(document.getDouble("paymentAmount"));
                    payment.setPaymentStatus((PaymentStatus) document.get("paymentStatus")); //fix payment status here
                    return mapper.map(payment, PaymentDto.class);
                }).collect().asList();
    }

    public Uni<Void> addPayment(Payment payment){
        Document document = new Document()
                .append("paymentDate", payment.getPaymentDate())
                .append("paymentAmount", payment.getPaymentAmount())
                .append("paymentStatus", payment.getPaymentStatus());
        return getCollection().insertOne(document)
                .onItem().ignore().andContinueWithNull();
    }
    private ReactiveMongoCollection<Document> getCollection(){
        return mongoClient.getDatabase("stargaze").getCollection("payments");
    }
}

