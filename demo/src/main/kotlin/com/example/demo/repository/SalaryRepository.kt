package com.example.demo.repository

import com.example.demo.model.SalaryPayment
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface SalaryRepository : JpaRepository<SalaryPayment, Int>