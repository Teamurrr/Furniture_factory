package com.example.demo.model

import jakarta.persistence.*


@Entity
@Table(name = "Employees")
data class Employee(

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Int? = null,

    val full_name: String? = null,

    val position: String? = null,

    val address: String? = null,

    val phone_number: Int? = null
)