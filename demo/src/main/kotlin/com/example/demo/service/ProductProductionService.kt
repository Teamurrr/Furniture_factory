package com.example.demo.service

import org.springframework.jdbc.core.JdbcTemplate
import org.springframework.jdbc.core.simple.SimpleJdbcCall
import org.springframework.stereotype.Service
import java.util.*

@Service
class ProductProductionService(
    private val jdbcTemplate: JdbcTemplate
) {

    fun createProduction(product: Int, quantity: Float, date: String, employee: Int) {

        val checkCall = SimpleJdbcCall(jdbcTemplate)
            .withProcedureName("check_rawmaterials")

        val params = mapOf(
            "product_id" to product,
            "quantity" to quantity
        )

        val result = checkCall.execute(params)

        val check = result["result"] as? Int ?: 0

        if (check == 1) {
            throw RuntimeException("Not enough raw materials")
        }

        jdbcTemplate.update(
            "EXEC add_production ?, ?, ?, ?",
            product,
            quantity,
            date,
            employee
        )
    }
}