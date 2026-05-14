package com.example.demo.model

import jakarta.persistence.*

@Entity
@Table(name = "Finished_products")
data class FinishedProduct(

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Int? = null,

    val name: String,

    @ManyToOne
    @JoinColumn(name = "unitOfMeasurement")
    val unitOfMeasure: UnitOfMeasurement,

    val quantity: Float,

    val amount: Float
)