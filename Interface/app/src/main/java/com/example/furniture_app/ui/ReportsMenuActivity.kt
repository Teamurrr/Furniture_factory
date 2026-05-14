package com.example.furniture_app.ui

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.example.furniture_app.R

class ReportsMenuActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_reports)

        findViewById<Button>(R.id.btnPurchases).setOnClickListener {
            startActivity(Intent(this, PurchasesReportActivity::class.java))
        }

        findViewById<Button>(R.id.btnSales).setOnClickListener {
            startActivity(Intent(this, SalesReportActivity::class.java))
        }

        findViewById<Button>(R.id.btnProduction).setOnClickListener {
            startActivity(Intent(this, ProductionReportActivity::class.java))
        }

        findViewById<Button>(R.id.btnSalary).setOnClickListener {
            startActivity(Intent(this, SalaryReportActivity::class.java))
        }

        findViewById<Button>(R.id.btnCredits).setOnClickListener {
            startActivity(Intent(this, CreditsReportActivity::class.java))
        }
    }
}