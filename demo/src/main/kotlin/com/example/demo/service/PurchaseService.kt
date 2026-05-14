package com.example.demo.service

import com.example.demo.model.RawMaterialPurchase
import com.example.demo.repository.PurchaseRepository
import jakarta.persistence.EntityManager
import jakarta.persistence.ParameterMode
import org.springframework.jdbc.core.JdbcTemplate
import org.springframework.jdbc.core.simple.SimpleJdbcCall
import org.springframework.stereotype.Service
@Service
class PurchaseService(
    private val jdbcTemplate: JdbcTemplate
) {

    fun save(purchase: RawMaterialPurchase) {

        val result = SimpleJdbcCall(jdbcTemplate)
            .withProcedureName("CheckBudget")
            .execute(
                mapOf(
                    "amount" to purchase.amount
                )
            )

        val check = result["result"] as Int

        if (check == 1) {
            throw RuntimeException("Not enough budget")
        }

        SimpleJdbcCall(jdbcTemplate)
            .withProcedureName("InsertPurchase")
            .execute(
                mapOf(
                    "raw_material_id" to purchase.rawMaterial?.id,
                    "quantity" to purchase.quantity,
                    "amount" to purchase.amount,
                    "date" to purchase.date,
                    "employee_id" to purchase.employee?.id
                )
            )
    }
}