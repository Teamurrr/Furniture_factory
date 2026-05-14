package com.example.demo.repository

import com.example.demo.model.UnitOfMeasurement
import org.springframework.data.jpa.repository.JpaRepository

interface UnitOfMeasurementRepository : JpaRepository<UnitOfMeasurement, Int>