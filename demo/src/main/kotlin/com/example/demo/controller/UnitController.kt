package com.example.demo.controller

import com.example.demo.model.UnitOfMeasurement
import com.example.demo.repository.UnitOfMeasurementRepository
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/units")
class UnitController(
    private val repository: UnitOfMeasurementRepository
) {

    @GetMapping
    fun getAll(): List<UnitOfMeasurement> =
        repository.findAll()

    @PostMapping
    fun create(@RequestBody unit: UnitOfMeasurement): UnitOfMeasurement =
        repository.save(unit)
}