package reservation.service;

import flight.exceptions.FlightException;
import reservation.exception.ReservationException;
import reservation.mappers.ReservationMapper;
import reservation.models.dto.RevenueAggregationDto;
import flight.service.FlightService;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import lombok.extern.slf4j.Slf4j;
import reservation.models.dto.RevenueQueryParams;
import reservation.models.dto.*;
import reservation.models.enums.ReservationStatus;
import reservation.models.ReservationEntity;
import reservation.repository.ReservationRepository;
import shared.GlobalHibernateValidator;
import shared.PaginationQueryParams;
import shared.mongoUtils.MongoTransactionManager;
import ticket.models.TicketEntity;
import ticket.service.TicketService;
import user.service.UserService;

import java.util.List;

@Slf4j
@ApplicationScoped
public class ReservationService {
  @Inject ReservationRepository reservationRepository;

  @Inject FlightService flightService;

  @Inject UserService userService;

  @Inject TicketService ticketService;

  @Inject GlobalHibernateValidator validator;

  @Inject MongoTransactionManager mongoTransactionManager;

  public static final double PRICE_COACH = 50.0;
  public static final double PRICE_VIP = 250.0;
  public static final double PRICE_BUSINESS = 150.0;

  public static final double PRICE_CABIN = 20.0;
  public static final double PRICE_FREE_CARRY_ON = 30.0;
  public static final double PRICE_TROLLEY = 50.0;
  public static final double PRICE_CHECKED_IN = 75.0;

  public Uni<List<ReservationEntity>> listReservations(
      PaginationQueryParams paginationQueryParams) {
    return reservationRepository.listReservations(paginationQueryParams);
  }

  public Uni<Void> createReservation(CreateReservationDto createReservationDto) {
    return validator
        .validate(createReservationDto)
        .flatMap(validDto -> flightService.getTicketIfAvailable(validDto.getFlightNumber()))
        .flatMap(
            ticket -> {
              ReservationEntity reservation = ReservationMapper.toReservation(createReservationDto);
              return runReservationTransactions(reservation, ticket);
            });
  }

  private Uni<Void> runReservationTransactions(ReservationEntity reservation, TicketEntity ticket) {
    return mongoTransactionManager
        .start()
        .call(
            session ->
                reservationRepository
                    .insertReservation(reservation, session.getSession())
                    .flatMap(
                        insertResult ->
                            ticketService.updateTicket(
                                ticket.getId(),
                                reservation.getUserId(),
                                reservation.getId(),
                                reservation.getPrice(),
                                session.getSession()))
                    .flatMap(
                        updateResult ->
                            userService.decreaseBalance(
                                reservation.getUserId(),
                                reservation.getPrice(),
                                session.getSession())))
        .call(session -> mongoTransactionManager.commit(session.getSessionUni()))
        .replaceWithVoid();
  }

  public Uni<ReservationDto> findByUserIdAndFlightNumber(String userId, String flightNumber) {
    return reservationRepository.findByUserIdAndFlightNumber(userId, flightNumber);
  }


  public Uni<UserRefundDto> runRefundTransactions(String userId, ReservationDto reservation, TicketEntity ticketEntity, String flightNumber) {
    return mongoTransactionManager
            .start()
            .flatMap(session ->
                    userService
                            .increaseBalance(userId, reservation.getPrice(), session.getSession())
                            .flatMap(updateResult ->
                                    ticketService
                                            .updateTicket(
                                                    ticketEntity.getId(),
                                                    ticketEntity.getUserId(),
                                                    ticketEntity.getReservationId(),
                                                    ticketEntity.getPrice(),
                                                    session.getSession()
                                            )
                                            .flatMap(ticketUpdateResult -> {
                                              reservation.setReservationStatus(ReservationStatus.REFUNDED);
                                              return flightService
                                                      .incrementCapacity(flightNumber, session.getSession());
                                            })
                                            .flatMap(flightUpdateResult ->
                                                    mongoTransactionManager
                                                            .commit(session.getSessionUni())
                                                            .replaceWith(
                                                                    new UserRefundDto(
                                                                            userId,
                                                                            1,
                                                                            0,
                                                                            ReservationStatus.REFUNDED
                                                                    )
                                                            )
                                            )
                            )
            );
  }




  public Uni<UserRefundDto>processRefund(String userId, String flightNumber){
    return reservationRepository
            .findByUserIdAndFlightNumber(userId, flightNumber)
            .flatMap(reservation->{
              return ticketService.findTicketByFlightNumber(flightNumber,userId)
                      .flatMap(ticketEntity->
                              runRefundTransactions(userId,reservation, ticketEntity, flightNumber));
            });
  }

  public Uni<List<UserReservationDto>> findUserWithMostRefundedTickets() {
    return reservationRepository.findUserWithMostRefundedTickets();
  }

  public Uni<List<UserReservationDto>> findUserWithMostReservedTickets() {
    return reservationRepository.findUserWithMostReservedTickets();
  }

  public Uni<List<ReservationRevenueDto>> salesOverTime(RevenueQueryParams revenueQueryParams) {
    return reservationRepository.salesOverTime(revenueQueryParams);
  }

  public Uni<List<ReservationRevenueDto>> facetedRevenue(RevenueQueryParams revenueQueryParams) {
    return reservationRepository.facetedRevenue(revenueQueryParams);
  }

  public Uni<List<RevenueAggregationDto>> revenueOverTime(RevenueQueryParams revenueQueryParams) {
    return reservationRepository.revenueOverTime(revenueQueryParams);
  }
}
