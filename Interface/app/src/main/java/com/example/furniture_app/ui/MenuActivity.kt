package com.example.furniture_app.ui

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.furniture_app.R
import com.example.furniture_app.api.RetrofitClient
import com.example.furniture_app.model.BudgetResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MenuActivity : AppCompatActivity() {

    private lateinit var rawMaterialsButton: Button
    private lateinit var purchaseButton: Button

    private lateinit var btnProduction: Button
    private lateinit var budgetAmountText: TextView


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_menu)

        rawMaterialsButton = findViewById(R.id.rawMaterialsButton)
        purchaseButton = findViewById(R.id.purchaseButton)
        btnProduction = findViewById<Button>(R.id.btnProduction)
        budgetAmountText = findViewById(R.id.txtBudgetAmount)



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

    override fun onResume() {
        super.onResume()
        loadBudget()
    }

    private fun loadBudget() {

        RetrofitClient.apiService.getBudget()
            .enqueue(object : Callback<BudgetResponse> {

                override fun onResponse(
                    call: Call<BudgetResponse>,
                    response: Response<BudgetResponse>
                ) {

                    if (response.isSuccessful) {
                        val amount = response.body()?.amount ?: 0.0
                        budgetAmountText.text = formatAmount(amount)
                    } else {
                        budgetAmountText.text = "Unavailable (${response.code()})"
                    }
                }

                override fun onFailure(call: Call<BudgetResponse>, t: Throwable) {
                    budgetAmountText.text = "Unavailable"
                    Toast.makeText(
                        this@MenuActivity,
                        "Failed to load budget: ${t.message}",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            })
    }

    private fun formatAmount(value: Double): String {
        val formatted = if (value % 1.0 == 0.0) {
            value.toInt().toString()
        } else {
            String.format(java.util.Locale.US, "%.2f", value)
        }

        return "$formatted som"
    }
}
