package com.example.demo.controller

import com.example.demo.model.ProductSale
import com.example.demo.repository.ProductSaleRepository
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/productsales")
class ProductSaleController(
    private val repository: ProductSaleRepository
) {

    @PostMapping
    fun createSale(@RequestBody sale: ProductSale): ProductSale {
        return repository.save(sale)
    }

    @GetMapping
    fun getSales(): List<ProductSale> {
        return repository.findAll()
    }
}