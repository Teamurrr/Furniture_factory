package com.example.demo.controller

import com.example.demo.model.RawMaterial
import com.example.demo.service.RawMaterialService
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/rawmaterials")
class RawMaterialController(
    private val service: RawMaterialService
) {

    @GetMapping
    fun getAll(): List<RawMaterial> {
        return service.getAll()
    }

    @GetMapping("/{id}")
    fun getById(@PathVariable id: Int): RawMaterial {
        return service.getAll().first { it.id == id }
    }

    @PostMapping
    fun create(@RequestBody rawMaterial: RawMaterial): RawMaterial {
        return service.save(rawMaterial)
    }

    @DeleteMapping("/{id}")
    fun delete(@PathVariable id: Int) {
        service.delete(id)
    }
}