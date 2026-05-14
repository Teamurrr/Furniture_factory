package com.example.furniture_app.ui

import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.example.furniture_app.R
import com.example.furniture_app.api.RetrofitClient
import com.example.furniture_app.model.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.text.SimpleDateFormat
import java.util.*

class ProductionActivity : AppCompatActivity() {

    private lateinit var productSpinner: Spinner
    private lateinit var employeeSpinner: Spinner
    private lateinit var quantityEdit: EditText
    private lateinit var produceButton: Button

    private var products: List<FinishedProduct> = listOf()
    private var employees: List<Employee> = listOf()

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_production)

        productSpinner = findViewById(R.id.productSpinner)
        employeeSpinner = findViewById(R.id.employeeSpinner)
        quantityEdit = findViewById(R.id.quantityEdit)
        produceButton = findViewById(R.id.produceButton)

        loadProducts()
        loadEmployees()

        produceButton.setOnClickListener {
            makeProduction()
        }
    }

    private fun loadProducts() {

        RetrofitClient.apiService.getProducts()
            .enqueue(object : Callback<List<FinishedProduct>> {

                override fun onResponse(
                    call: Call<List<FinishedProduct>>,
                    response: Response<List<FinishedProduct>>
                ) {

                    products = response.body() ?: listOf()
                    val names = products.mapNotNull { it.name }

                    val adapter = ArrayAdapter(
                        this@ProductionActivity,
                        android.R.layout.simple_spinner_item,
                        names
                    )

                    adapter.setDropDownViewResource(
                        android.R.layout.simple_spinner_dropdown_item
                    )

                    productSpinner.adapter = adapter
                }

                override fun onFailure(call: Call<List<FinishedProduct>>, t: Throwable) {

                    Toast.makeText(
                        this@ProductionActivity,
                        "Failed to load products",
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

                    employees = response.body() ?: listOf()

                    val names = employees.mapNotNull { it.fullName }

                    val adapter = ArrayAdapter(
                        this@ProductionActivity,
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
                        this@ProductionActivity,
                        "Failed to load employees",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            })
    }

    private fun makeProduction() {

        val quantity = quantityEdit.text.toString().toFloat()

        val productIndex = productSpinner.selectedItemPosition
        val employeeIndex = employeeSpinner.selectedItemPosition

        val date = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
            .format(Date())

        val production = ProductProduction(

            product = products[productIndex].id!!,
            quantity = quantity,
            date = date,
            employee = employees[employeeIndex].id!!
        )

        RetrofitClient.apiService.produceProduct(production)
            .enqueue(object : Callback<Void> {

                override fun onResponse(call: Call<Void>, response: Response<Void>) {

                    if (response.isSuccessful) {

                        Toast.makeText(
                            this@ProductionActivity,
                            "Production successful",
                            Toast.LENGTH_SHORT
                        ).show()

                    } else {

                        Toast.makeText(
                            this@ProductionActivity,
                            "Server error",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }

                override fun onFailure(call: Call<Void>, t: Throwable) {

                    Toast.makeText(
                        this@ProductionActivity,
                        "Connection error",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            })
    }
}