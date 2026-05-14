package com.example.demo.model

import jakarta.persistence.*

@Entity
@Table(name = "Positions")
data class Position(

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Int? = null,

    val job_title: String
)