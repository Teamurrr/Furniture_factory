package com.example.furniture_app.model

data class PurchaseRequest(
    val rawMaterial: IdWrapper,
    val quantity: Float,
    val amount: Float,
    val date: String,
    val employee: IdWrapper
)

