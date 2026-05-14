package com.example.demo.controller

import org.springframework.jdbc.core.JdbcTemplate
import org.springframework.web.bind.annotation.*
import org.springframework.jdbc.core.ConnectionCallback

@RestController
@RequestMapping("/credit")
class CreditController(private val jdbcTemplate: JdbcTemplate) {

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
}