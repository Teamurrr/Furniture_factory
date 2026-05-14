package com.example.demo.model

import jakarta.persistence.*

@Entity
@Table(name = "SalaryPayments")
data class SalaryPayment(

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Int? = 0,

    val employee_id: Int,

    val amount: Double,

    val payment_date: String
)