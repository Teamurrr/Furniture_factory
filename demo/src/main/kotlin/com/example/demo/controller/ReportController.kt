package com.example.demo.controller
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.jdbc.core.JdbcTemplate

@RestController
@RequestMapping("/reports")
class ReportController {

    @Autowired
    lateinit var jdbcTemplate: JdbcTemplate

    @GetMapping("/purchases")
    fun getPurchases(): List<Map<String, Any?>> {
        val sql = "SELECT * FROM vw_raw_material_purchases"
        return jdbcTemplate.queryForList(sql)
    }

    @GetMapping("/sales")
    fun getSales(): List<Map<String, Any?>> {
        val sql = "SELECT * FROM vw_product_sales"
        return jdbcTemplate.queryForList(sql)
    }

    @GetMapping("/production")
    fun getProduction(): List<Map<String, Any?>> {
        val sql = "SELECT * FROM vw_product_production"
        return jdbcTemplate.queryForList(sql)
    }

    @GetMapping("/salary")
    fun getSalary(): List<Map<String, Any?>> {
        val sql = "SELECT * FROM vw_salary_payments"
        return jdbcTemplate.queryForList(sql)
    }

    @GetMapping("/credits")
    fun getCredits(): List<Map<String, Any?>> {
        val sql = "SELECT * FROM vw_budget_credits"
        return jdbcTemplate.queryForList(sql)
    }
}