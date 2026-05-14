package com.example.furniture_app.ui

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.furniture_app.R
import com.example.furniture_app.api.ApiService
import com.example.furniture_app.api.RetrofitClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class CreditActivity : AppCompatActivity() {

    private lateinit var amountEditText: EditText
    private lateinit var button: Button
    private lateinit var apiService: ApiService

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_credit)

        amountEditText = findViewById(R.id.creditAmount)
        button = findViewById(R.id.buttonCredit)

        apiService = RetrofitClient.apiService

        button.setOnClickListener {

            val amountText = amountEditText.text.toString()

            if (amountText.isEmpty()) {
                Toast.makeText(this, "Enter amount", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val amount = amountText.toDouble()

            Toast.makeText(this, "Processing credit...", Toast.LENGTH_SHORT).show()

            Handler(Looper.getMainLooper()).postDelayed({

                apiService.takeCredit(amount).enqueue(object : Callback<Int> {

                    override fun onResponse(call: Call<Int>, response: Response<Int>) {

                        val result = response.body()

                        if (result == 0) {
                            Toast.makeText(this@CreditActivity, "Credit approved", Toast.LENGTH_SHORT).show()
                        } else {
                            Toast.makeText(this@CreditActivity, "You already have an active credit", Toast.LENGTH_SHORT).show()
                        }

                    }

                    override fun onFailure(call: Call<Int>, t: Throwable) {
                        Toast.makeText(this@CreditActivity, "Error: ${t.message}", Toast.LENGTH_SHORT).show()
                    }

                })

            }, 3000)

        }
    }
}