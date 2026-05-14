package com.example.demo.model

data class CreditHistoryItem(
    val id: Int,
    val amount: Double,
    val interest: Double,
    val totalToPay: Double,
    val date: String?,
    val status: String
)
