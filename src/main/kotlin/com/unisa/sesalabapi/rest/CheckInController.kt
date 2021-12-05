package com.unisa.sesalabapi.rest

import com.unisa.sesalab.ods.dto.CheckInDTO
import com.unisa.sesalab.ods.factory.CheckInFactory
import com.unisa.sesalab.ods.model.CheckIn
import com.unisa.sesalab.ods.service.checkin.CheckInRuleService
import com.unisa.sesalab.ods.service.checkin.CheckInService
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import java.time.OffsetDateTime

@RestController
@RequestMapping("/api/v1/checkin")
class CheckInController(
    private val checkInService: CheckInService,
    private val checkInRuleService: CheckInRuleService
): BaseController()
{
    private val baseUrl = "/api/v1/checkin"
    private val logger: Logger = LoggerFactory.getLogger(CheckInController::class.java)

    @PostMapping
    fun checkin(@RequestBody checkInDTO: CheckInDTO): ResponseEntity<HttpStatus>
    {
        this.logger.info("Received POST Request ${this.baseUrl}")
        val reservationToCheckIn = this.checkInRuleService.checkNewCheckIn(checkInDTO, this.getUserLogger())
        val checkInToSave = CheckIn(reservationToCheckIn, OffsetDateTime.now())
        checkInToSave.isValid = this.checkInRuleService.isInTime(CheckInFactory.createCheckIn(checkInToSave))
        this.checkInService.saveCheckIn(checkInToSave)
        return ResponseEntity(HttpStatus.OK)
    }
}