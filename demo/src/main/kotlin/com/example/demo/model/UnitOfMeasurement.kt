package com.example.demo.model

import jakarta.persistence.*

@Entity
@Table(name = "Units_of_measurement")
data class UnitOfMeasurement(

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Int? = null,

    val name: String? = null
)