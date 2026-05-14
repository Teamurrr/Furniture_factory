package com.example.furniture_app.ui

import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.example.furniture_app.R
import com.example.furniture_app.api.RetrofitClient
import com.example.furniture_app.model.Employee
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SalaryActivity : AppCompatActivity() {

    private lateinit var spinnerEmployee: Spinner
    private lateinit var editSalary: EditText
    private lateinit var btnPaySalary: Button

    private var employeeList: List<Employee> = listOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_salary)

        spinnerEmployee = findViewById(R.id.spinnerEmployee)
        editSalary = findViewById(R.id.editAmount)
        btnPaySalary = findViewById(R.id.btnPay)

        loadEmployees()

        btnPaySalary.setOnClickListener {
            paySalary()
        }
    }

    private fun loadEmployees() {

        RetrofitClient.apiService.getEmployees()
            .enqueue(object : Callback<List<Employee>> {

                override fun onResponse(
                    call: Call<List<Employee>>,
                    response: Response<List<Employee>>
                ) {

                    if (response.isSuccessful && response.body() != null) {

                        employeeList = response.body()!!

                        val names = employeeList.map { it.fullName }

                        val adapter = ArrayAdapter(
                            this@SalaryActivity,
                            android.R.layout.simple_spinner_item,
                            names
                        )

                        adapter.setDropDownViewResource(
                            android.R.layout.simple_spinner_dropdown_item
                        )

                        spinnerEmployee.adapter = adapter
                    }
                }

                override fun onFailure(call: Call<List<Employee>>, t: Throwable) {

                    Toast.makeText(
                        this@SalaryActivity,
                        "Error loading employees",
                        Toast.LENGTH_LONG
                    ).show()
                }
            })
    }

    private fun paySalary() {

        if (employeeList.isEmpty()) {
            Toast.makeText(this, "Employees not loaded", Toast.LENGTH_LONG).show()
            return
        }

        val employee = employeeList[spinnerEmployee.selectedItemPosition]

        val amountText = editSalary.text.toString()

        if (amountText.isEmpty()) {
            Toast.makeText(this, "Enter salary amount", Toast.LENGTH_LONG).show()
            return
        }

        val amount = amountText.toDouble()

        RetrofitClient.apiService.paySalary(
            employeeId = employee.id,
            amount = amount
        ).enqueue(object : Callback<String> {

            override fun onResponse(call: Call<String>, response: Response<String>) {

                if (response.isSuccessful) {

                    Toast.makeText(
                        this@SalaryActivity,
                        response.body(),
                        Toast.LENGTH_LONG
                    ).show()

                } else {

                    Toast.makeText(
                        this@SalaryActivity,
                        "Server error: ${response.code()}",
                        Toast.LENGTH_LONG
                    ).show()
                }
            }

            override fun onFailure(call: Call<String>, t: Throwable) {

                Toast.makeText(
                    this@SalaryActivity,
                    "Salary payment error: ${t.message}",
                    Toast.LENGTH_LONG
                ).show()
            }
        })
    }
}