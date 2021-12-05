package com.unisa.sesalabapi.dto

import development.kit.asset.AssetState

data class SeatDTO(
    val id: Long,
    val name: String,
    val state: AssetState,
    val tagNfcValue: String
)