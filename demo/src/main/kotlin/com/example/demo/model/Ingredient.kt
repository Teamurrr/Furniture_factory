package com.example.demo.model

import jakarta.persistence.*

@Entity
@Table(name = "Ingredients")
data class Ingredient(

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Int? = null,

    @ManyToOne
    @JoinColumn(name = "products")
    val product: FinishedProduct,

    @ManyToOne
    @JoinColumn(name = "raw_materials")
    val rawMaterial: RawMaterial,

    val quantity: Float
)