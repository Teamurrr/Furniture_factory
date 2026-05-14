package com.example.demo.controller

import com.example.demo.service.CreditService
import com.example.demo.model.CreditHistoryItem
import org.springframework.jdbc.core.JdbcTemplate
import org.springframework.web.bind.annotation.*
import org.springframework.jdbc.core.ConnectionCallback
import org.springframework.http.ResponseEntity

@RestController
@RequestMapping("/credit")
class CreditController(
    private val jdbcTemplate: JdbcTemplate,
    private val creditService: CreditService
) {

    @PostMapping
    fun takeCredit(@RequestParam amount: Double): Int {

        val result = jdbcTemplate.execute(ConnectionCallback<Int> { connection ->

            val callable = connection.prepareCall("{call take_business_credit(?, ?)}")
            callable.setDouble(1, amount)
            callable.registerOutParameter(2, java.sql.Types.INTEGER)

            callable.execute()

            callable.getInt(2)
        })

        return result ?: 1
    }

    @GetMapping
    fun getCreditHistory(): List<CreditHistoryItem> {
        return creditService.getCreditsHistory()
    }

    @PostMapping("/{id}/repay")
    fun repayCredit(@PathVariable id: Int): ResponseEntity<String> {
        return ResponseEntity.ok(creditService.repayCredit(id))
    }
}
