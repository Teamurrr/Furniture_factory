package com.example.demo.repository

import com.example.demo.model.RawMaterialPurchase
import org.springframework.data.jpa.repository.JpaRepository

interface PurchaseRepository : JpaRepository<RawMaterialPurchase, Int>