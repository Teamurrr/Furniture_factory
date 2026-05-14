package com.example.demo.service

import com.example.demo.model.ProductSale
import com.example.demo.repository.FinishedProductRepository
import com.example.demo.repository.ProductSaleRepository
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Service
import org.springframework.web.server.ResponseStatusException

@Service
class ProductSaleService(
    private val saleRepository: ProductSaleRepository,
    private val finishedProductRepository: FinishedProductRepository
) {

    fun createSale(sale: ProductSale): ProductSale {
        val product = finishedProductRepository.findById(sale.products)
            .orElseThrow {
                ResponseStatusException(HttpStatus.NOT_FOUND, "Product not found")
            }

        if (sale.quantity <= 0.0) {
            throw ResponseStatusException(
                HttpStatus.BAD_REQUEST,
                "Quantity must be greater than zero"
            )
        }

        if (sale.quantity > product.quantity) {
            throw ResponseStatusException(
                HttpStatus.BAD_REQUEST,
                "Not enough product quantity available"
            )
        }

        return saleRepository.save(sale)
    }
}
