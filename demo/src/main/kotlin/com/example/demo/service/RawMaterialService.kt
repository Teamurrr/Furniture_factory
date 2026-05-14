package com.example.demo.service

import com.example.demo.model.RawMaterial
import com.example.demo.repository.RawMaterialRepository
import org.springframework.stereotype.Service

@Service
class RawMaterialService(
    private val repository: RawMaterialRepository
) {

    fun getAll(): List<RawMaterial> {
        return repository.findAll()
    }

    fun save(rawMaterial: RawMaterial): RawMaterial {
        return repository.save(rawMaterial)
    }

    fun delete(id: Int) {
        repository.deleteById(id)
    }
}