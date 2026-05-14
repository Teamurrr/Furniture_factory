package com.example.furniture_app.ui

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.widget.Button
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.cardview.widget.CardView
import androidx.core.content.ContextCompat
import com.example.furniture_app.R
import com.example.furniture_app.api.ApiService
import com.example.furniture_app.api.RetrofitClient
import com.example.furniture_app.model.CreditHistoryItem
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class CreditActivity : AppCompatActivity() {

    private lateinit var amountEditText: EditText
    private lateinit var button: Button
    private lateinit var historyContainer: LinearLayout
    private lateinit var apiService: ApiService

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_credit)

        amountEditText = findViewById(R.id.creditAmount)
        button = findViewById(R.id.buttonCredit)
        historyContainer = findViewById(R.id.creditHistoryContainer)

        apiService = RetrofitClient.apiService
        loadCreditHistory()

        button.setOnClickListener {

            val amountText = amountEditText.text.toString()

            if (amountText.isEmpty()) {
                Toast.makeText(this, "Enter amount", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val amount = amountText.toDouble()
            if (amount <= 0) {
                Toast.makeText(this, "Amount must be greater than zero", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            Toast.makeText(this, "Processing credit...", Toast.LENGTH_SHORT).show()

            Handler(Looper.getMainLooper()).postDelayed({

                apiService.takeCredit(amount).enqueue(object : Callback<Int> {

                    override fun onResponse(call: Call<Int>, response: Response<Int>) {

                        val result = response.body()

                        if (result == 0) {
                            Toast.makeText(this@CreditActivity, "Credit approved", Toast.LENGTH_SHORT).show()
                            amountEditText.text.clear()
                            loadCreditHistory()
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

    private fun loadCreditHistory() {

        apiService.getCreditHistory().enqueue(object : Callback<List<CreditHistoryItem>> {

            override fun onResponse(
                call: Call<List<CreditHistoryItem>>,
                response: Response<List<CreditHistoryItem>>
            ) {
                if (response.isSuccessful) {
                    val credits = response.body().orEmpty()
                    renderCreditHistory(credits)
                } else {
                    renderHistoryMessage("Failed to load credit history (${response.code()})")
                }
            }

            override fun onFailure(call: Call<List<CreditHistoryItem>>, t: Throwable) {
                renderHistoryMessage("Failed to load credit history: ${t.message}")
            }
        })
    }

    private fun renderCreditHistory(credits: List<CreditHistoryItem>) {
        historyContainer.removeAllViews()

        if (credits.isEmpty()) {
            renderHistoryMessage("No credit history yet")
            return
        }

        credits.forEach { credit ->
            historyContainer.addView(createCreditCard(credit))
        }
    }

    private fun renderHistoryMessage(message: String) {
        historyContainer.removeAllViews()
        historyContainer.addView(
            TextView(this).apply {
                text = message
                setTextColor(ContextCompat.getColor(context, R.color.brand_muted))
                textSize = 15f
                setPadding(8, 12, 8, 12)
            }
        )
    }

    private fun createCreditCard(credit: CreditHistoryItem): CardView {
        val status = credit.status
        val isActive = status.equals("ACTIVE", ignoreCase = true)
        val date = credit.date ?: "-"

        val content = LinearLayout(this).apply {
            orientation = LinearLayout.VERTICAL
            setPadding(24, 24, 24, 24)
        }

        content.addView(createInfoText("Status: $status"))
        content.addView(createInfoText("Date: $date"))
        content.addView(createInfoText("Amount: ${formatAmount(credit.amount)}"))
        content.addView(createInfoText("Interest: ${formatAmount(credit.interest)}%"))
        content.addView(createInfoText("Total to pay: ${formatAmount(credit.totalToPay)}"))

        if (isActive) {
            content.addView(
                Button(this).apply {
                    text = "Repay Credit"
                    isAllCaps = false
                    setTextColor(ContextCompat.getColor(context, R.color.white))
                    background = ContextCompat.getDrawable(context, R.drawable.bg_button_secondary)
                    setOnClickListener { repayCredit(credit.id) }
                }
            )
        }

        return CardView(this).apply {
            radius = 24f
            cardElevation = 8f
            setCardBackgroundColor(
                ContextCompat.getColor(
                    context,
                    if (isActive) R.color.brand_panel else R.color.brand_surface
                )
            )
            val params = LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
            )
            params.bottomMargin = 16
            layoutParams = params
            addView(content)
        }
    }

    private fun repayCredit(creditId: Int) {

        apiService.repayCredit(creditId).enqueue(object : Callback<String> {
            override fun onResponse(call: Call<String>, response: Response<String>) {
                if (response.isSuccessful) {
                    Toast.makeText(
                        this@CreditActivity,
                        response.body() ?: "Credit repaid successfully",
                        Toast.LENGTH_SHORT
                    ).show()
                    loadCreditHistory()
                } else {
                    Toast.makeText(
                        this@CreditActivity,
                        "Not enough budget to repay credit",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }

            override fun onFailure(call: Call<String>, t: Throwable) {
                Toast.makeText(
                    this@CreditActivity,
                    "Error: ${t.message}",
                    Toast.LENGTH_SHORT
                ).show()
            }
        })
    }

    private fun createInfoText(text: String): TextView {
        return TextView(this).apply {
            this.text = text
            setTextColor(ContextCompat.getColor(context, R.color.brand_ink))
            textSize = 15f
            setPadding(0, 0, 0, 10)
        }
    }

    private fun formatAmount(value: Double): String {
        return if (value % 1.0 == 0.0) {
            value.toInt().toString()
        } else {
            String.format(java.util.Locale.US, "%.2f", value)
        }
    }

}
