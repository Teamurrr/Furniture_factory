package com.example.demo.controller

import com.example.demo.model.FinishedProduct
import com.example.demo.repository.FinishedProductRepository
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/finishedproducts")
class FinishedProductController(
    private val repository: FinishedProductRepository
) {

    @GetMapping
    fun getAll(): List<FinishedProduct> {
        return repository.findAll()
    }
}