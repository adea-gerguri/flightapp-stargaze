package reservation.service;

import baggage.enums.BaggageType;
import flight.service.FlightService;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import reservation.enums.ReservationStatus;
import reservation.enums.SeatType;
import reservation.exception.ReservationException;
import reservation.models.ReservationEntity;
import reservation.models.dto.CreateReservationDto;
import reservation.models.dto.ReservationDto;
import reservation.models.dto.UserRefundDto;
import reservation.models.dto.UserReservationDto;
import reservation.repository.ReservationRepository;
import shared.GlobalHibernateValidator;
import shared.mongoUtils.InsertResult;
import user.exceptions.UserException;
import user.service.UserService;

import java.time.LocalDateTime;
import java.util.List;

@ApplicationScoped
public class ReservationService {
    @Inject
    ReservationRepository reservationRepository;

    @Inject
    FlightService flightService;

    @Inject
    UserService userService;

    @Inject
    GlobalHibernateValidator validator;

    public Uni<List<ReservationEntity>> listReservations(int skip, int limit) {
        return reservationRepository.listReservations(skip, limit)
                .onFailure()
                .transform(e->new ReservationException(e.getMessage(),404));
    }

    @Transactional
    public Uni<InsertResult> createReservation(CreateReservationDto dto) {
        return validator.validate(dto)
                .onItem().transformToUni(validDto -> {
                    SeatType seatType = validDto.getSeatSelection();
                    BaggageType baggageType = validDto.getBaggage().getBaggageType();
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

                    return flightService.checkAndUpdateFlightAvailability(validDto.getFlightNumber())
                            .onItem().transformToUni(updatedFlight -> {
                                return reservationRepository.insertReservation(reservation)
                                        .onItem().transformToUni(insertResult -> {
                                            return userService.decreaseBalance(validDto.getUserId(), totalPrice)
                                                    .onItem().transform(decreaseResult -> insertResult);
                                        });
                            });
                });
    }


    public double calculateAdditionalPrice(SeatType seatSelection, BaggageType baggageType) {
        double seatPrice = 0.0;
        double baggagePrice = 0.0;

        switch (seatSelection) {
            case COACH:
                seatPrice = 50.0;
                break;
            case BUSINESS:
                seatPrice = 150.0;
                break;
            case VIP:
                seatPrice = 250.0;
                break;
        }

        switch (baggageType) {
            case CABIN:
                baggagePrice = 20.0;
                break;
            case FREE_CARRY_ON:
                baggagePrice = 30.0;
                break;
            case TROLLEY:
                baggagePrice = 50.0;
                break;
            case CHECKED_IN:
                baggagePrice = 75.0;
                break;
        }

        return seatPrice + baggagePrice;
    }

    public Uni<ReservationDto> findByUserIdAndFlightNumber(String userId, String flightNumber) {
        return reservationRepository.findByUserIdAndFlightNumber(userId, flightNumber)
                .onItem().transform(reservationDto -> {
                    if (reservationDto != null) {
                        return reservationDto;
                    } else {
                        throw new ReservationException("Reservation not found for user", 404);
                    }
                })
                .onFailure(ReservationException.class)
                .recoverWithItem(ex -> {
                    throw new ReservationException("Reservation not found for user", 404);
                });
    }


    public Uni<UserReservationDto> findUserWithMostReservations() {
        return reservationRepository.findUserWithMostReservations()
                .onFailure(ReservationException.class)
                .recoverWithItem(() -> {
                    throw new ReservationException("No reservations found", 404);
                });
    }

    @Transactional
    public Uni<UserRefundDto> processRefund(String userId, String flightNumber) {
        return reservationRepository.findByUserIdAndFlightNumber(userId, flightNumber)
                .onItem().transformToUni(reservation -> {
                    System.out.println(reservation);
                    if (reservation == null) {
                        throw new ReservationException("Reservation not found", 404);
                    }
                    if (reservation.getReservationStatus() == ReservationStatus.REFUNDED) {
                        throw new ReservationException("Reservation already refunded", 400);
                    }
                    double price = reservation.getPrice();
                    return userService.increaseBalance(userId, price)
                            .onItem().transformToUni(updateResult -> {
                                if (updateResult.getModifiedCount() > 0) {

                                    reservation.setReservationStatus(ReservationStatus.REFUNDED);

                                    return flightService.incrementCapacity(flightNumber)
                                            .onItem().transformToUni(flightUpdateResult -> {
                                                return Uni.createFrom().item(new UserRefundDto(
                                                        userId,
                                                        1,
                                                        0,
                                                        ReservationStatus.REFUNDED
                                                ));
                                            });
                                } else {
                                    throw new UserException("Failed to update user balance", 500);
                                }
                            });
                });
    }

    public Uni<UserReservationDto> findUserWithMostRefundedTickets() {
        return reservationRepository.findUserWithMostRefundedTickets()
                .onItem().transform(reservation -> {
                    if (reservation != null) {
                        return reservation;
                    } else {
                        throw new ReservationException("No refunded reservations found", 404);
                    }
                })
                .onFailure(ReservationException.class)
                .recoverWithItem(() -> {
                    throw new ReservationException("No refunded reservations found", 404);
                });
    }











}
