package com.example.furniture_app.model

data class ProductMaterialRequirement(
    val rawMaterialId: Int?,
    val rawMaterialName: String?,
    val unitName: String?,
    val quantityPerUnit: Float,
    val availableQuantity: Float?
)
