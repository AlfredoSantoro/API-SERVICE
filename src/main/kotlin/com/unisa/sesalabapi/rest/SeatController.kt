package com.unisa.sesalabapi.rest

import com.unisa.sesalab.ods.factory.AssetFactory
import com.unisa.sesalab.ods.factory.ReservationEntityFactory
import com.unisa.sesalab.ods.service.reservation.ReservationService
import com.unisa.sesalab.ods.service.seat.SeatService
import com.unisa.sesalabapi.dto.SeatDTO
import development.kit.asset.AssetManager
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/v1/seats")
class SeatController(
    private val seatService: SeatService,
    private val reservationService: ReservationService
)
{
    private val baseUrl = "/api/v1/asset"
    private val logger: Logger = LoggerFactory.getLogger(SeatController::class.java)

    @GetMapping("/state")
    fun getAssetState(): ResponseEntity<List<SeatDTO>>
    {
        this.logger.info("### Received GET request ${this.baseUrl}/state")
        val seatDTOsList = arrayListOf<SeatDTO>()
        val allSeats = this.seatService.findAllSeats()
        val seatsOccupiedNow = this.reservationService.viewAllReservationsOnGoing().groupBy { it.studySeatReserved.id }
        allSeats.forEach { seatEntity ->
            seatEntity.canBeBooked = seatEntity.canBeBooked
            val state = AssetManager.getCurrentAssetState(AssetFactory.createSeat(seatEntity),
                if ( seatsOccupiedNow[seatEntity.id]?.first() !== null )
                {
                ReservationEntityFactory.createBaseReservation(seatsOccupiedNow[seatEntity.id]?.first()!!)
                } else null
            )
            seatDTOsList.add(SeatDTO(seatEntity.id!!, seatEntity.name, state, seatEntity.tagNfc.value))
        }
        return ResponseEntity.ok(seatDTOsList)
    }
}