package com.example.furniture_app.ui

import android.os.Bundle
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.widget.doAfterTextChanged
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.furniture_app.R
import com.example.furniture_app.api.RetrofitClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class CreditsReportActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_credit_report)

        val title = findViewById<TextView>(R.id.txtReportTitle)
        val filterInput = findViewById<EditText>(R.id.edtReportFilter)
        val headerContainer = findViewById<LinearLayout>(R.id.headerContainer)
        val recycler = findViewById<androidx.recyclerview.widget.RecyclerView>(R.id.recyclerReport)

        title.text = "Credits Report"
        recycler.layoutManager = LinearLayoutManager(this)

        RetrofitClient.apiService.getCreditsReport().enqueue(object :
            Callback<List<Map<String, Any>>> {

            override fun onResponse(
                call: Call<List<Map<String, Any>>>,
                response: Response<List<Map<String, Any>>>
            ) {

                if (response.isSuccessful) {

                    val data = response.body() ?: emptyList()
                    val adapter = CreditReportAdapter(data)

                    recycler.adapter = adapter
                    renderHeader(headerContainer, adapter.getFormattedColumns())
                    filterInput.doAfterTextChanged { text ->
                        adapter.filter(text?.toString().orEmpty())
                    }

                }
            }

            override fun onFailure(call: Call<List<Map<String, Any>>>, t: Throwable) {

                t.printStackTrace()

            }
        })
    }

    private fun renderHeader(container: LinearLayout, columns: List<String>) {
        container.removeAllViews()

        columns.forEach { column ->
            val headerCell = TextView(this).apply {
                layoutParams = LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.WRAP_CONTENT, 1f)
                setPadding(24, 20, 24, 20)
                text = column
                setTextColor(ContextCompat.getColor(context, android.R.color.black))
                textSize = 14f
                setTypeface(typeface, android.graphics.Typeface.BOLD)
            }
            container.addView(headerCell)
        }
    }
}
