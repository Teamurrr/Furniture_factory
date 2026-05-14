package com.example.demo.model

import jakarta.persistence.*

@Entity
@Table(name = "Purchase_of_raw_materials")
data class RawMaterialPurchase(

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Int? = null,

    @ManyToOne
    @JoinColumn(name = "raw_materials")
    val rawMaterial: RawMaterial? = null,

    val quantity: Float? = null,

    val amount: Float? = null,

    val date: String? = null,

    @ManyToOne
    @JoinColumn(name = "employee")
    val employee: Employee? = null
)