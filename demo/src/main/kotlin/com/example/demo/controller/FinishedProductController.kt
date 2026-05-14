package com.example.demo.controller

import com.example.demo.model.FinishedProduct
import com.example.demo.model.ProductMaterialRequirement
import com.example.demo.repository.FinishedProductRepository
import com.example.demo.repository.IngredientRepository
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/finishedproducts")
class FinishedProductController(
    private val repository: FinishedProductRepository,
    private val ingredientRepository: IngredientRepository
) {

    @GetMapping
    fun getAll(): List<FinishedProduct> {
        return repository.findAll()
    }

    @GetMapping("/{id}/materials")
    fun getMaterials(@PathVariable id: Int): List<ProductMaterialRequirement> {
        return ingredientRepository.findAllByProductId(id).map { ingredient ->
            ProductMaterialRequirement(
                rawMaterialId = ingredient.rawMaterial.id,
                rawMaterialName = ingredient.rawMaterial.name,
                unitName = ingredient.rawMaterial.unitOfMeasure?.name,
                quantityPerUnit = ingredient.quantity,
                availableQuantity = ingredient.rawMaterial.quantity
            )
        }
    }
}
