package com.example.demo.controller

import org.springframework.jdbc.core.JdbcTemplate
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/budget")
class BudgetController(
    private val jdbcTemplate: JdbcTemplate
) {

    @GetMapping
    fun getBudget(): Map<String, Double> {
        val amount = jdbcTemplate.queryForObject(
            "SELECT TOP 1 budget_amount FROM Budget",
            Double::class.java
        ) ?: 0.0

        return mapOf("amount" to amount)
    }
}
