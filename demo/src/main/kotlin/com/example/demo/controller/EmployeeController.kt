package com.example.demo.controller

import com.example.demo.model.Employee
import com.example.demo.repository.EmployeeRepository
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/employees")
class EmployeeController(

    private val employeeRepository: EmployeeRepository

) {

    @GetMapping
    fun getEmployees(): List<Employee> {

        return employeeRepository.findAll()
    }
}