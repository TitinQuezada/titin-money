package com.example.titinmoney.models

import org.threeten.bp.LocalDateTime


data class Transaction(
    val id: Int,
    val date: LocalDateTime,
    val description: String,
    val quantity: Double,
    val type: Int,
)
