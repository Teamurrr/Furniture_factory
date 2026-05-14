package com.example.demo.controller

import com.example.demo.service.SalaryService
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/salary")
class SalaryController(
    private val salaryService: SalaryService
) {

    @PostMapping
    fun paySalary(
        @RequestParam employee_id: Int,
        @RequestParam(required = false) amount: Double?
    ): String {

        return salaryService.paySalary(employee_id, amount)
    }

    @GetMapping("/test")
    fun test(): String {
        return "controller works"
    }
}