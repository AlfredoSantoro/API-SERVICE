package com.unisa.sesalabapi.rest

import com.unisa.sesalab.ods.dto.ReservationInsertDTO
import com.unisa.sesalab.ods.model.Reservation
import com.unisa.sesalab.ods.service.reservation.ReservationService
import com.unisa.sesalab.ods.service.seat.SeatService
import com.unisa.sesalab.ods.service.user.UserService
import com.unisa.sesalabapi.dto.OnGoingReservationDTO
import com.unisa.sesalabapi.dto.ReservationDTO
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import java.time.Duration

@RestController
@RequestMapping("/api/v1/reservations")
class ReservationController(
    private val reservationService: ReservationService,
    private val userService: UserService,
    private val seatService: SeatService
): BaseController()
{
    private val baseUrl = "/api/v1/reservations"
    private val logger: Logger = LoggerFactory.getLogger(ReservationController::class.java)

    @PostMapping
    fun createReservation(@RequestBody reservationDTO: ReservationDTO): ResponseEntity<HttpStatus>
    {
        this.logger.info("### Received POST request ${this.baseUrl}/create")
        val account = this.userService.findUserByUsername(reservationDTO.accountId)
        val seat = this.seatService.findById(reservationDTO.seatId)
        val result: HttpStatus = if ( account == null || seat == null)
        {
            HttpStatus.BAD_REQUEST
        }
        else
        {
            this.reservationService.createReservation(ReservationInsertDTO("Reservation ${seat.name}",
            reservationDTO.start, Duration.ofHours(1), account, seat))
            HttpStatus.OK
        }
        return ResponseEntity(result)
    }

    @GetMapping("/me")
    fun getReservationsMe(): ResponseEntity<OnGoingReservationDTO?>
    {
        this.logger.info("### Received GET request ${this.baseUrl}/me")
        val userLogged = this.getUserLogger()
        val reservation = this.reservationService.findReservationOnGoingByUser(userLogged)
        val onGoingReservationDTO = if ( reservation !== null )
        {
            OnGoingReservationDTO(
                reservation.id!!,
                reservation.studySeatReserved.name,
                reservation.start,
                reservation.end,
                reservation.studySeatReserved.tagNfc.value)
        } else null

        return ResponseEntity(onGoingReservationDTO, HttpStatus.OK)
    }

    @GetMapping("/{id}")
    fun getReservation(@PathVariable(name = "id") id: Long): ResponseEntity<Reservation?>
    {
        this.logger.info("### Received GET request ${this.baseUrl}/{$id}")
        return ResponseEntity(this.reservationService.findReservationById(id), HttpStatus.OK)
    }
}