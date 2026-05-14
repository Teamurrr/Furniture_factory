package com.example.demo.service

import com.example.demo.repository.SalaryRepository
import org.springframework.jdbc.core.JdbcTemplate
import org.springframework.stereotype.Service
import java.sql.Connection
import java.sql.Types

@Service
class SalaryService(
    private val jdbcTemplate: JdbcTemplate,
    private val salaryRepository: SalaryRepository
) {

    fun paySalary(employeeId: Int, amount: Double?): String {

        val salary = amount ?: jdbcTemplate.queryForObject(
            "SELECT salary FROM Employees WHERE id = ?",
            Double::class.java,
            employeeId
        )!!

        val result = jdbcTemplate.execute { con: Connection ->

            val cs = con.prepareCall("{call CheckBudgetForSalary(?, ?)}")

            cs.setDouble(1, salary)
            cs.registerOutParameter(2, Types.INTEGER)

            cs.execute()

            cs.getInt(2)
        }

        if (result == 1) {
            return "Not enough budget"
        }

        jdbcTemplate.update(
            "EXEC InsertSalaryPayment ?, ?",
            employeeId,
            salary
        )

        return "Salary paid successfully"
    }
}