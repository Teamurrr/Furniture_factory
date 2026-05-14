package com.example.demo.repository

import com.example.demo.model.Ingredient
import org.springframework.data.jpa.repository.JpaRepository

interface IngredientRepository : JpaRepository<Ingredient, Int> {
    fun findAllByProductId(productId: Int): List<Ingredient>
}
