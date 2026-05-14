package com.example.demo.model

import jakarta.persistence.*
@Entity
@Table(name = "Raw_materials")
data class RawMaterial(

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Int? = null,

    val name: String? = null,

    val quantity: Float? = null,

    val amount: Float? = null,

    @ManyToOne
    @JoinColumn(name = "unit_of_measurement")
    val unitOfMeasure: UnitOfMeasurement? = null
)