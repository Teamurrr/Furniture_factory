package com.example.demo.repository

import com.example.demo.model.FinishedProduct
import org.springframework.data.jpa.repository.JpaRepository

interface FinishedProductRepository : JpaRepository<FinishedProduct, Int>