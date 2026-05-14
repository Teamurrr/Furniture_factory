package com.example.furniture_app.model

data class BusinessCredit(
    val id: Int,
    val amount: Float,
    val interest: Float,
    val totalToPay: Float,
    val date: String?,
    val status: String
)
