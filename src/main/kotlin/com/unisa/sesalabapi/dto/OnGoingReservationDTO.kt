package com.unisa.sesalabapi.dto

import java.time.OffsetDateTime

data class OnGoingReservationDTO(
    val reservationId: Long,
    val seatName: String,
    val start: OffsetDateTime,
    val end: OffsetDateTime,
    val seatTagNFC: String
)