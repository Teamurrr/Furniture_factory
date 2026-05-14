package com.example.demo.model

import jakarta.persistence.*

@Entity
@Table(name = "Budget")
data class Budget(

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Int? = null,

    val budget_amount: Float
)