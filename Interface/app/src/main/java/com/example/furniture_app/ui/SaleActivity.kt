package com.example.furniture_app.ui

import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.example.furniture_app.R
import com.example.furniture_app.api.RetrofitClient
import com.example.furniture_app.model.Employee
import com.example.furniture_app.model.FinishedProduct
import com.example.furniture_app.model.ProductSale
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SaleActivity : AppCompatActivity() {

    private lateinit var spinnerProducts: Spinner
    private lateinit var spinnerEmployees: Spinner
    private lateinit var editQuantity: EditText
    private lateinit var textAmount: TextView
    private lateinit var btnSell: Button

    private var productsList: List<FinishedProduct> = listOf()
    private var employeesList: List<Employee> = listOf()

    private var totalAmount = 0.0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.sale_activity)

        spinnerProducts = findViewById(R.id.spinnerProducts)
        spinnerEmployees = findViewById(R.id.spinnerEmployees)
        editQuantity = findViewById(R.id.editQuantity)
        textAmount = findViewById(R.id.textAmount)
        btnSell = findViewById(R.id.btnSell)

        loadProducts()
        loadEmployees()

        btnSell.setOnClickListener {

            if (productsList.isEmpty() || employeesList.isEmpty()) {
                Toast.makeText(this, "Data not loaded", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val quantity = editQuantity.text.toString().toDoubleOrNull()

            if (quantity == null) {
                Toast.makeText(this, "Enter quantity", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val selectedProduct = productsList[spinnerProducts.selectedItemPosition]
            val selectedEmployee = employeesList[spinnerEmployees.selectedItemPosition]

            val price = selectedProduct.amount / selectedProduct.quantity
            totalAmount = quantity * price

            val sale = ProductSale(
                products = selectedProduct.id,
                quantity = quantity,
                amount = totalAmount,
                date = "2026-03-13",
                employee = selectedEmployee.id
            )

            RetrofitClient.apiService.createSale(sale)
                .enqueue(object : Callback<ProductSale> {

                    override fun onResponse(
                        call: Call<ProductSale>,
                        response: Response<ProductSale>
                    ) {

                        if (response.isSuccessful) {

                            Toast.makeText(
                                this@SaleActivity,
                                "Sale successful",
                                Toast.LENGTH_SHORT
                            ).show()

                            editQuantity.text.clear()
                            textAmount.text = "Amount: 0"

                        } else {

                            Toast.makeText(
                                this@SaleActivity,
                                "Error creating sale",
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    }

                    override fun onFailure(call: Call<ProductSale>, t: Throwable) {

                        Toast.makeText(
                            this@SaleActivity,
                            "Connection error",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                })
        }

        editQuantity.setOnKeyListener { _, _, _ ->
            calculateAmount()
            false
        }
    }

    private fun calculateAmount() {

        if (productsList.isEmpty()) return

        val quantity = editQuantity.text.toString().toDoubleOrNull() ?: return

        val selectedProduct = productsList[spinnerProducts.selectedItemPosition]

        val price = selectedProduct.amount / selectedProduct.quantity

        totalAmount = quantity * price

        textAmount.text = "Amount: $totalAmount"
    }

    private fun loadProducts() {

        RetrofitClient.apiService.getFinishedProducts()
            .enqueue(object : Callback<List<FinishedProduct>> {

                override fun onResponse(
                    call: Call<List<FinishedProduct>>,
                    response: Response<List<FinishedProduct>>
                ) {

                    if (response.isSuccessful) {

                        productsList = response.body() ?: listOf()

                        val names = productsList.map { it.name }

                        val adapter = ArrayAdapter(
                            this@SaleActivity,
                            android.R.layout.simple_spinner_item,
                            names
                        )

                        adapter.setDropDownViewResource(
                            android.R.layout.simple_spinner_dropdown_item
                        )

                        spinnerProducts.adapter = adapter
                    }
                }

                override fun onFailure(call: Call<List<FinishedProduct>>, t: Throwable) {

                    Toast.makeText(
                        this@SaleActivity,
                        "Error loading products",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            })
    }

    private fun loadEmployees() {

        RetrofitClient.apiService.getEmployees()
            .enqueue(object : Callback<List<Employee>> {

                override fun onResponse(
                    call: Call<List<Employee>>,
                    response: Response<List<Employee>>
                ) {

                    if (response.isSuccessful) {

                        employeesList = response.body() ?: listOf()

                        val names = employeesList.map { it.fullName }

                        val adapter = ArrayAdapter(
                            this@SaleActivity,
                            android.R.layout.simple_spinner_item,
                            names
                        )

                        adapter.setDropDownViewResource(
                            android.R.layout.simple_spinner_dropdown_item
                        )

                        spinnerEmployees.adapter = adapter
                    }
                }

                override fun onFailure(call: Call<List<Employee>>, t: Throwable) {

                    Toast.makeText(
                        this@SaleActivity,
                        "Error loading employees",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            })
    }
}