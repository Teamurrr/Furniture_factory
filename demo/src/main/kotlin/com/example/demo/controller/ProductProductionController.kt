package com.example.demo.controller

import com.example.demo.model.ProductProduction
import com.example.demo.service.ProductProductionService
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/production")
class ProductProductionController(
    private val service: ProductProductionService
) {

    @PostMapping
    fun createProduction(@RequestBody production: ProductProduction) {

        service.createProduction(
            production.product!!,
            production.quantity!!,
            production.date.toString(),
            production.employee!!
        )
    }
}