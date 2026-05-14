package com.example.demo.model

import jakarta.persistence.*

@Entity
@Table(name = "Product_sales")
data class ProductSale(

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Int? = 0,

    val products: Int = 0,

    val quantity: Double = 0.0,

    val amount: Double = 0.0,

    val date: String = "",

    val employee: Int = 0
)