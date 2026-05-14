package com.example.demo.controller

import com.example.demo.model.ProductSale
import com.example.demo.repository.ProductSaleRepository
import com.example.demo.service.ProductSaleService
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/productsales")
class ProductSaleController(
    private val repository: ProductSaleRepository,
    private val service: ProductSaleService
) {

    @PostMapping
    fun createSale(@RequestBody sale: ProductSale): ProductSale {
        return service.createSale(sale)
    }

    @GetMapping
    fun getSales(): List<ProductSale> {
        return repository.findAll()
    }
}
