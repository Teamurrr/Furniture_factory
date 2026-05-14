package com.example.furniture_app.model

data class ProductSale(
    val products: Int,
    val quantity: Double,
    val amount: Double,
    val date: String,
    val employee: Int
)