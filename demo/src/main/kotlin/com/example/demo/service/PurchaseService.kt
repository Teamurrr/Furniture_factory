package com.example.demo.service

import com.example.demo.model.RawMaterialPurchase
import org.springframework.jdbc.core.JdbcTemplate
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class PurchaseService(
    private val jdbcTemplate: JdbcTemplate
) {

    @Transactional
    fun save(purchase: RawMaterialPurchase) {
        val amount = purchase.amount?.toDouble()
            ?: throw RuntimeException("Invalid purchase amount")
        val quantity = purchase.quantity?.toDouble()
            ?: throw RuntimeException("Invalid purchase quantity")
        val rawMaterialId = purchase.rawMaterial?.id
            ?: throw RuntimeException("Raw material is required")
        val employeeId = purchase.employee?.id
            ?: throw RuntimeException("Employee is required")
        val date = purchase.date
            ?: throw RuntimeException("Date is required")

        val currentBudget = jdbcTemplate.queryForObject(
            "SELECT TOP 1 budget_amount FROM Budget",
            Double::class.java
        ) ?: 0.0

        if (currentBudget < amount) {
            throw RuntimeException("Not enough budget")
        }

        jdbcTemplate.update(
            """
                INSERT INTO Purchase_of_raw_materials
                (raw_materials, quantity, amount, date, employee)
                VALUES (?, ?, ?, ?, ?)
            """.trimIndent(),
            rawMaterialId,
            quantity,
            amount,
            date,
            employeeId
        )

        jdbcTemplate.update(
            "UPDATE Budget SET budget_amount = budget_amount - ?",
            amount
        )

        jdbcTemplate.update(
            """
                UPDATE Raw_materials
                SET
                    quantity = COALESCE(quantity, 0) + ?,
                    amount = COALESCE(amount, 0) + ?
                WHERE id = ?
            """.trimIndent(),
            quantity,
            amount,
            rawMaterialId
        )
    }
}
