package reservation.service;

import baggage.models.enums.BaggageType;
import reservation.models.dto.RevenueAggregationDto;
import flight.service.FlightService;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import lombok.extern.slf4j.Slf4j;
import reservation.models.dto.RevenueQueryParams;
import reservation.models.dto.*;
import reservation.models.enums.ReservationStatus;
import reservation.models.enums.SeatType;
import reservation.models.ReservationEntity;
import reservation.repository.ReservationRepository;
import shared.GlobalHibernateValidator;
import shared.PaginationQueryParams;
import shared.mongoUtils.InsertResult;
import shared.mongoUtils.MongoTransactionManager;
import ticket.service.TicketService;
import user.service.UserService;

import java.time.LocalDateTime;
import java.util.List;

@Slf4j
@ApplicationScoped
public class ReservationService {
    @Inject
    ReservationRepository reservationRepository;

    @Inject
    FlightService flightService;

    @Inject
    UserService userService;

    @Inject
    TicketService ticketService;

    @Inject
    GlobalHibernateValidator validator;

    @Inject
    MongoTransactionManager mongoTransactionManager;

    public static final double PRICE_COACH = 50.0;
    public static final double PRICE_VIP = 250.0;
    public static final double PRICE_BUSINESS = 150.0;

    public static final double PRICE_CABIN = 20.0;
    public static final double PRICE_FREE_CARRY_ON = 30.0;
    public static final double PRICE_TROLLEY = 50.0;
    public static final double PRICE_CHECKED_IN = 75.0;

    public Uni<List<ReservationEntity>> listReservations(PaginationQueryParams paginationQueryParams) {
        return reservationRepository.listReservations(paginationQueryParams);
    }

    public Uni<InsertResult> createReservation(CreateReservationDto createReservationDto) {
        return validator.validate(createReservationDto)
                .flatMap(validDto ->
                        flightService.checkAndUpdateFlightAvailability(validDto.getFlightNumber())
                                .flatMap(updatedFlight ->
                                        mongoTransactionManager.execute()
                                                .flatMap(session -> {
                                                    SeatType seatType = validDto.getSeatSelection();
                                                    BaggageType baggageType = validDto.getBaggage();
                                                    double additionalPrice = calculateAdditionalPrice(seatType, baggageType);
                                                    double totalPrice = validDto.getPrice() + additionalPrice;

                                                    ReservationEntity reservation = new ReservationEntity();
                                                    reservation.setReservationDate(LocalDateTime.now());
                                                    reservation.setFlightNumber(validDto.getFlightNumber());
                                                    reservation.setUserId(validDto.getUserId());
                                                    reservation.setSeatSelection(validDto.getSeatSelection());
                                                    reservation.setSpecialAssistance(validDto.isSpecialAssistance());
                                                    reservation.setBaggage(validDto.getBaggage());
                                                    reservation.setPrice(totalPrice);
                                                    reservation.setReservationStatus(ReservationStatus.RESERVED);

                                                    return reservationRepository.insertReservation(reservation, session.getSession())
                                                            .flatMap(insertResult ->
                                                                    ticketService.findAvailableTicketByFlightNumber(validDto.getFlightNumber(), session.getSession())
                                                                            .flatMap(ticket ->
                                                                                    ticketService.updateTicket(ticket.getId(), validDto.getUserId(), reservation.getId(), totalPrice, session.getSession())
                                                                                            .flatMap(updateResult ->
                                                                                                    userService.decreaseBalance(validDto.getUserId(), totalPrice, session.getSession())
                                                                                                            .onItem().transform(result -> insertResult)
                                                                                            )
                                                                            )
                                                            )
                                                            .call(result -> mongoTransactionManager.commit(session.getSessionUni()))
                                                            .onFailure().call(error -> mongoTransactionManager.end(session.getSessionUni()));
                                                })
                                )
                );
    }


    public double calculateAdditionalPrice(SeatType seatSelection, BaggageType baggageType) {
        double seatPrice = 0.0;
        double baggagePrice = 0.0;

        switch (seatSelection) {
            case COACH:
                seatPrice = PRICE_COACH;
                break;
            case BUSINESS:
                seatPrice = PRICE_BUSINESS;
                break;
            case VIP:
                seatPrice = PRICE_VIP;
                break;
        }


        switch (baggageType) {
            case CABIN:
                baggagePrice = PRICE_CABIN;
                break;
            case FREE_CARRY_ON:
                baggagePrice = PRICE_FREE_CARRY_ON;
                break;
            case TROLLEY:
                baggagePrice = PRICE_TROLLEY;
                break;
            case CHECKED_IN:
                baggagePrice = PRICE_CHECKED_IN;
                break;
        }

        return seatPrice + baggagePrice;
    }

    public Uni<ReservationDto> findByUserIdAndFlightNumber(String userId, String flightNumber) {
        return reservationRepository.findByUserIdAndFlightNumber(userId, flightNumber);
    }


    public Uni<UserRefundDto> processRefund(String userId, String flightNumber) {
        return reservationRepository.findByUserIdAndFlightNumber(userId, flightNumber)
                .flatMap(reservation -> {
                    double price = reservation.getPrice();
                    return ticketService.findTicketByFlightNumber(flightNumber, userId)
                            .flatMap(ticketEntity ->
                                    mongoTransactionManager.execute()
                                            .flatMap(session ->
                                                    userService.increaseBalance(userId, price, session.getSession())
                                                            .flatMap(updateResult ->
                                                                    ticketService.updateTicket(
                                                                                    ticketEntity.getId(),
                                                                                    ticketEntity.getUserId(),
                                                                                    ticketEntity.getReservationId(),
                                                                                    ticketEntity.getPrice(),
                                                                                    session.getSession()
                                                                            )
                                                                            .flatMap(ticketUpdateResult -> {
                                                                                reservation.setReservationStatus(ReservationStatus.REFUNDED);
                                                                                return flightService.incrementCapacity(flightNumber, session.getSession())
                                                                                        .flatMap(flightUpdateResult ->
                                                                                                Uni.createFrom().item(new UserRefundDto(
                                                                                                        userId,
                                                                                                        1,
                                                                                                        0,
                                                                                                        ReservationStatus.REFUNDED
                                                                                                ))
                                                                                        );
                                                                            })
                                                            )
                                                            .call(result -> mongoTransactionManager.commit(session.getSessionUni()))
                                                            .onFailure().call(error -> mongoTransactionManager.end(session.getSessionUni()))
                                            )
                            );
                });
    }


    public Uni<List<UserReservationDto>> findUserWithMostRefundedTickets() {
        return reservationRepository.findUserWithMostRefundedTickets();
    }

    public Uni<List<UserReservationDto>> findUserWithMostReservedTickets(){
        return reservationRepository.findUserWithMostReservedTickets();
    }

    public Uni<List<ReservationRevenueDto>> salesOverTime(RevenueQueryParams revenueQueryParams){
        return reservationRepository.salesOverTime(revenueQueryParams);
    }

    public Uni<List<ReservationRevenueDto>> facetedRevenue(RevenueQueryParams revenueQueryParams){
        return reservationRepository.facetedRevenue(revenueQueryParams);
    }

    public Uni<List<RevenueAggregationDto>> revenueOverTime(RevenueQueryParams revenueQueryParams){
        return reservationRepository.revenueOverTime(revenueQueryParams);
    }




}









