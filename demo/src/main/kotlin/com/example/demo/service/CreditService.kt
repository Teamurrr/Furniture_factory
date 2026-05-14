package com.example.demo.service

import com.example.demo.model.CreditHistoryItem
import org.springframework.http.HttpStatus
import org.springframework.jdbc.core.JdbcTemplate
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import org.springframework.web.server.ResponseStatusException

@Service
class CreditService(
    private val jdbcTemplate: JdbcTemplate
) {

    fun getCreditsHistory(): List<CreditHistoryItem> {
        val sql = """
            SELECT
                id,
                amount,
                interest,
                total_to_pay AS totalToPay,
                CONVERT(varchar(10), date, 23) AS date,
                status
            FROM Business_Credit
            ORDER BY
                CASE WHEN status = 'ACTIVE' THEN 0 ELSE 1 END,
                date DESC,
                id DESC
        """.trimIndent()

        return jdbcTemplate.query(sql) { rs, _ ->
            CreditHistoryItem(
                id = rs.getInt("id"),
                amount = rs.getDouble("amount"),
                interest = rs.getDouble("interest"),
                totalToPay = rs.getDouble("totalToPay"),
                date = rs.getString("date"),
                status = rs.getString("status")
            )
        }
    }

    @Transactional
    fun repayCredit(id: Int): String {
        val credit = jdbcTemplate.queryForList(
            """
                SELECT id, total_to_pay, status
                FROM Business_Credit
                WHERE id = ?
            """.trimIndent(),
            id
        ).firstOrNull() ?: throw ResponseStatusException(HttpStatus.NOT_FOUND, "Credit not found")

        val status = credit["status"]?.toString().orEmpty()

        if (!status.equals("ACTIVE", ignoreCase = true)) {
            throw ResponseStatusException(HttpStatus.BAD_REQUEST, "Credit is already repaid")
        }

        val totalToPay = (credit["total_to_pay"] as? Number)?.toDouble()
            ?: (credit["total_to_pay"]?.toString()?.toDoubleOrNull())
            ?: throw ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid credit amount")

        val currentBudget = jdbcTemplate.queryForObject(
            "SELECT TOP 1 budget_amount FROM Budget",
            Double::class.java
        ) ?: 0.0

        if (currentBudget < totalToPay) {
            throw ResponseStatusException(HttpStatus.BAD_REQUEST, "Not enough budget to repay credit")
        }

        jdbcTemplate.update(
            "UPDATE Budget SET budget_amount = budget_amount - ?",
            totalToPay
        )

        jdbcTemplate.update(
            "UPDATE Business_Credit SET status = 'PAID' WHERE id = ?",
            id
        )

        return "Credit repaid successfully"
    }
}
