package com.example.demo.controller

import com.example.demo.model.RawMaterialPurchase
import com.example.demo.repository.PurchaseRepository
import com.example.demo.service.PurchaseService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
@RestController
@RequestMapping("/rawmaterialpurchase")
class PurchaseController(
    private val service: PurchaseService
) {

    @PostMapping
    fun purchase(@RequestBody purchase: RawMaterialPurchase): ResponseEntity<String> {

        return try {

            service.save(purchase)

            ResponseEntity.ok("Purchase successful")

        } catch (e: Exception) {

            ResponseEntity.badRequest().body("Not enough budget")
        }
    }
}