package com.unisa.sesalabapi.dto

import java.time.OffsetDateTime

data class ReservationDTO(
    val start: OffsetDateTime,
    val accountId: String,
    val seatId: Long
)