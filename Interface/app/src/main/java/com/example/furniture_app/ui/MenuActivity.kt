package com.example.furniture_app.ui

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.example.furniture_app.R

class MenuActivity : AppCompatActivity() {

    private lateinit var rawMaterialsButton: Button
    private lateinit var purchaseButton: Button

    private lateinit var btnProduction: Button


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_menu)

        rawMaterialsButton = findViewById(R.id.rawMaterialsButton)
        purchaseButton = findViewById(R.id.purchaseButton)
        btnProduction = findViewById<Button>(R.id.btnProduction)



        rawMaterialsButton.setOnClickListener {

            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }

        purchaseButton.setOnClickListener {

            val intent = Intent(this, PurchaseActivity::class.java)
            startActivity(intent)
        }


        val btnOpenSale = findViewById<Button>(R.id.btnOpenSale)

        btnOpenSale.setOnClickListener {

            val intent = Intent(this, SaleActivity::class.java)
            startActivity(intent)

        }

        val btnSalary = findViewById<Button>(R.id.btnSalary)
        btnSalary.setOnClickListener {

            val intent = Intent(this, SalaryActivity::class.java)
            startActivity(intent)

        }


        val creditButton = findViewById<Button>(R.id.btnCredit)

        creditButton.setOnClickListener {

            val intent = Intent(this, CreditActivity::class.java)
            startActivity(intent)

        }


        findViewById<Button>(R.id.btnReports).setOnClickListener {

            startActivity(Intent(this, ReportsMenuActivity::class.java))

        }




        btnProduction.setOnClickListener {
            startActivity(Intent(this, ProductionActivity::class.java))
        }
    }
}