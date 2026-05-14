package com.example.furniture_app.ui

import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.example.furniture_app.R
import com.example.furniture_app.api.RetrofitClient
import com.example.furniture_app.model.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import androidx.core.widget.addTextChangedListener

class PurchaseActivity : AppCompatActivity() {

    private lateinit var materialSpinner: Spinner
    private lateinit var employeeSpinner: Spinner
    private lateinit var quantityEdit: EditText
    private lateinit var buyButton: Button
    private lateinit var priceText: TextView
    private lateinit var totalText: TextView

    private var materials: List<RawMaterial> = listOf()
    private var employees: List<Employee> = listOf()

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_purchase)

        materialSpinner = findViewById(R.id.materialSpinner)
        employeeSpinner = findViewById(R.id.employeeSpinner)
        quantityEdit = findViewById(R.id.quantityEdit)
        buyButton = findViewById(R.id.buyButton)
        priceText = findViewById(R.id.priceText)
        totalText = findViewById(R.id.totalText)

        loadMaterials()
        loadEmployees()

        materialSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {

            override fun onItemSelected(parent: AdapterView<*>, view: View?, position: Int, id: Long) {

                val price = materials[position].amount ?: 0f
                priceText.text = "Price: $price"
                updateTotal()
            }

            override fun onNothingSelected(parent: AdapterView<*>) {}
        }

        buyButton.setOnClickListener {
            makePurchase()
        }

        quantityEdit.addTextChangedListener {
            updateTotal()
        }
    }

    private fun updateTotal() {

        if (materials.isEmpty()) return

        val quantity = quantityEdit.text.toString().toFloatOrNull() ?: 0f

        val materialIndex = materialSpinner.selectedItemPosition
        val price = materials[materialIndex].amount ?: 0f

        val total = quantity * price

        totalText.text = "Total: $total"
    }

    private fun loadMaterials() {

        val api = RetrofitClient.apiService

        api.getRawMaterials().enqueue(object : Callback<List<RawMaterial>> {

            override fun onResponse(
                call: Call<List<RawMaterial>>,
                response: Response<List<RawMaterial>>
            ) {

                materials = response.body() ?: listOf()

                val names = materials.map { it.name }

                val adapter = ArrayAdapter(
                    this@PurchaseActivity,
                    android.R.layout.simple_spinner_item,
                    names
                )

                adapter.setDropDownViewResource(
                    android.R.layout.simple_spinner_dropdown_item
                )

                materialSpinner.adapter = adapter
            }

            override fun onFailure(call: Call<List<RawMaterial>>, t: Throwable) {

                Toast.makeText(
                    this@PurchaseActivity,
                    "Failed to load materials",
                    Toast.LENGTH_SHORT
                ).show()
            }
        })
    }

    private fun loadEmployees() {

        val api = RetrofitClient.apiService

        api.getEmployees().enqueue(object : Callback<List<Employee>> {

            override fun onResponse(
                call: Call<List<Employee>>,
                response: Response<List<Employee>>
            ) {

                employees = response.body() ?: listOf()

                val names = employees.mapNotNull { it.fullName }

                val adapter = ArrayAdapter(
                    this@PurchaseActivity,
                    android.R.layout.simple_spinner_item,
                    names
                )

                adapter.setDropDownViewResource(
                    android.R.layout.simple_spinner_dropdown_item
                )

                employeeSpinner.adapter = adapter
            }

            override fun onFailure(call: Call<List<Employee>>, t: Throwable) {

                Toast.makeText(
                    this@PurchaseActivity,
                    "Failed to load employees",
                    Toast.LENGTH_SHORT
                ).show()
            }
        })
    }

    private fun makePurchase() {

        val quantity = quantityEdit.text.toString().toFloat()

        val materialIndex = materialSpinner.selectedItemPosition
        val price = materials[materialIndex].amount ?: 0f

        val total = quantity * price
        val employeeIndex = employeeSpinner.selectedItemPosition

        val purchase = PurchaseRequest(

            rawMaterial = IdWrapper(materials[materialIndex].id!!),

            quantity = quantity,

            amount = total,

            date = "2026-03-12",

            employee = IdWrapper(employees[employeeIndex].id!!)
        )

        val api = RetrofitClient.apiService

        api.purchaseRawMaterial(purchase).enqueue(object : Callback<Void> {

            override fun onResponse(call: Call<Void>, response: Response<Void>) {

                if (response.isSuccessful) {

                    Toast.makeText(
                        this@PurchaseActivity,
                        "Purchase successful",
                        Toast.LENGTH_SHORT
                    ).show()

                } else {

                    Toast.makeText(
                        this@PurchaseActivity,
                        "Server error ${response.code()}",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }

            override fun onFailure(call: Call<Void>, t: Throwable) {

                Toast.makeText(
                    this@PurchaseActivity,
                    "Connection error",
                    Toast.LENGTH_SHORT
                ).show()
            }
        })
    }
}