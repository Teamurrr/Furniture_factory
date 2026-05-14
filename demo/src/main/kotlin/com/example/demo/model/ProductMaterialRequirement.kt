package com.example.demo.model

data class ProductMaterialRequirement(
    val rawMaterialId: Int?,
    val rawMaterialName: String?,
    val unitName: String?,
    val quantityPerUnit: Float,
    val availableQuantity: Float?
)
