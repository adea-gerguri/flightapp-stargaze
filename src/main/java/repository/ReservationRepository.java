package repository;

import io.quarkus.mongodb.reactive.ReactiveMongoClient;
import io.quarkus.mongodb.reactive.ReactiveMongoCollection;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import model.dto.ReservationDto;
import model.mapper.MapperService;
import model.mapper.ReservationMapper;
import model.view.Baggage;
import model.view.Reservation;
import org.bson.Document;

import java.sql.Date;
import java.util.List;

@ApplicationScoped
public class ReservationRepository {

    @Inject
    ReactiveMongoClient mongoClient;

    @Inject
    ReservationMapper reservationMapper;

    @Inject
    MapperService mapper;

    public Uni<List<ReservationDto>> listReservations(){
        return getCollection().find()
                .map(document ->{
                    Reservation reservation = new Reservation();
                    reservation.setReservationDate((Date) document.getDate("reservationDate"));
                    reservation.setBaggage((Baggage) document.get("baggage"));
                    reservation.setSpecialAssistance(document.getBoolean("specialAssistance"));
                    return mapper.map(reservation, ReservationDto.class);
                }).collect().asList();
    }
    public Uni<Void> addReservation(Reservation reservation){
        Document document = new Document()
                .append("reservationDate", reservation.getReservationDate())
                .append("specialAssistance", reservation.isSpecialAssistance())
                .append("baggage", reservation.getBaggage());
        return getCollection().insertOne(document)
                .onItem().ignore().andContinueWithNull();
    }

    private ReactiveMongoCollection<Document> getCollection(){
        return mongoClient.getDatabase("stargaze").getCollection("reservations");
    }




//    @Inject
//    MongoClient mongoClient;
//
//    public Uni<Reservations> reservationList(){
//        List<Reservations> reservationsList = new ArrayList<>();
//        MongoCursor<Document> cursor = mongoClient.listDatabases().iterator();
//        try{
//            while(cursor.hasNext()){
//                Document document = cursor.next();
//                Reservations reservation = new Reservations();
//                reservation.setReservationDate((Date) document.getDate("reservationDate"));
//                reservation.setBaggage((Baggage) document.get("baggage"));
//            }
//        }finally{
//            cursor.close();
//        }
//        return (Uni<Reservations>) reservationsList;
//    }
//    public void add(Reservations reservation){
//        Document document = new Document()
//                .append("reservationDate", reservation.getReservationDate())
//                .append("seatSelection", reservation.getSeatSelection())
//                .append("specialAssistance", reservation.isSpecialAssistance())
//                .append("baggage", reservation.getBaggage());
//        getCollection().insertOne(document);
//    }
//
//    private MongoCollection getCollection(){
//        return mongoClient.getDatabase("stargaze").getCollection("reservations");
//    }
}

