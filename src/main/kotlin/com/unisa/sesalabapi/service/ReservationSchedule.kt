package com.unisa.sesalabapi.service

import com.unisa.sesalab.ods.service.reservation.ReservationService
import com.unisa.sesalab.ods.service.setting.SettingService
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Value
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Service
@Service
class ReservationSchedule(
    private val reservationService: ReservationService,
    private val settingService: SettingService,
    @Value("\${settings.default-names.checkInFrequency}")
    private val checkInFrequencySettingName: String
)
{
    private val logger: Logger = LoggerFactory.getLogger(ReservationSchedule::class.java)

    private val defaultCheckInFrequencyInMinutes = 15

    val checkInFrequency = this.settingService.findByName(this.checkInFrequencySettingName)?.value
        ?: this.defaultCheckInFrequencyInMinutes

    @Scheduled(fixedRateString = "\${schedule.reservations.check-in}")
    fun terminateReservationsWithoutCheckIn()
    {
        this.logger.info("### START Reservation without check-in process ###")

        val reservationsToDeleteIds = this.reservationService.getReservationsToTerminate(
            this.reservationService.viewAllReservationsOnGoing(), this.checkInFrequency)

        // Terminate all ongoing reservations without check-in stored in the reservationsToDeleteIds list
        if ( reservationsToDeleteIds.isNotEmpty())
        {
            this.reservationService.terminateAllReservationsByIds(reservationsToDeleteIds)
        }

        this.logger.info("### END Reservation without check-in process ###")
    }
}