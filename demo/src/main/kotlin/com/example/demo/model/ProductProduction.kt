package com.example.demo.model

import jakarta.persistence.*
import java.time.LocalDate

@Entity
@Table(name = "Product_production")
class ProductProduction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Int? = null

    @Column(name = "Product")
    var product: Int? = null

    @Column(name = "Quantity")
    var quantity: Float? = null

    @Column(name = "Date")
    var date: LocalDate? = null

    @Column(name = "Employee")
    var employee: Int? = null
}