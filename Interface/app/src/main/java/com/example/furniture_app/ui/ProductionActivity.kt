package com.example.furniture_app.ui

import android.os.Bundle
import android.graphics.Typeface
import android.view.View
import android.widget.AdapterView
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.doAfterTextChanged
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
    private lateinit var materialsSummary: TextView
    private lateinit var materialsTable: TableLayout

    private var products: List<FinishedProduct> = listOf()
    private var employees: List<Employee> = listOf()
    private var productMaterials: List<ProductMaterialRequirement> = listOf()

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_production)

        productSpinner = findViewById(R.id.productSpinner)
        employeeSpinner = findViewById(R.id.employeeSpinner)
        quantityEdit = findViewById(R.id.quantityEdit)
        produceButton = findViewById(R.id.produceButton)
        materialsSummary = findViewById(R.id.materialsSummary)
        materialsTable = findViewById(R.id.materialsTable)

        loadProducts()
        loadEmployees()
        bindMaterialsPreview()

        produceButton.setOnClickListener {
            makeProduction()
        }
    }

    private fun bindMaterialsPreview() {

        productSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                val productId = products.getOrNull(position)?.id ?: return
                loadProductMaterials(productId)
            }

            override fun onNothingSelected(parent: AdapterView<*>?) = Unit
        }

        quantityEdit.doAfterTextChanged {
            renderMaterials()
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

                    if (products.isNotEmpty()) {
                        loadProductMaterials(products.first().id)
                    } else {
                        productMaterials = listOf()
                        renderMaterials()
                    }
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

    private fun loadProductMaterials(productId: Int) {

        RetrofitClient.apiService.getProductMaterials(productId)
            .enqueue(object : Callback<List<ProductMaterialRequirement>> {

                override fun onResponse(
                    call: Call<List<ProductMaterialRequirement>>,
                    response: Response<List<ProductMaterialRequirement>>
                ) {

                    productMaterials = response.body() ?: listOf()
                    renderMaterials()
                }

                override fun onFailure(
                    call: Call<List<ProductMaterialRequirement>>,
                    t: Throwable
                ) {

                    productMaterials = listOf()
                    materialsSummary.text = "Failed to load required materials."
                    renderMaterials(showEmptyState = true)
                }
            })
    }

    private fun renderMaterials(showEmptyState: Boolean = false) {

        materialsTable.removeAllViews()
        addTableHeader()

        if (showEmptyState || productMaterials.isEmpty()) {
            if (!showEmptyState) {
                materialsSummary.text = "No material composition found for this product."
            }
            addEmptyStateRow("Material list is unavailable.")
            return
        }

        val productionQuantity = quantityEdit.text.toString().toFloatOrNull() ?: 1f
        materialsSummary.text = buildString {
            append("For ")
            append(productionQuantity)
            append(" unit(s) of production you need:")
        }

        productMaterials.forEach { material ->
            val requiredAmount = material.quantityPerUnit * productionQuantity
            val unitName = material.unitName ?: "unit"
            val materialName = material.rawMaterialName ?: "Unnamed material"
            val availableQuantity = material.availableQuantity
            val requiredText = "${formatNumber(requiredAmount)} $unitName"
            val availableText = if (availableQuantity == null) {
                "unknown"
            } else {
                "${formatNumber(availableQuantity)} $unitName"
            }

            addMaterialTableRow(
                materialName = materialName,
                requiredText = requiredText,
                availableText = availableText,
                isEnough = availableQuantity == null || availableQuantity >= requiredAmount
            )
        }
    }

    private fun addTableHeader() {

        val headerRow = TableRow(this)
        headerRow.addView(createTableCell("Material", true))
        headerRow.addView(createTableCell("Needed", true))
        headerRow.addView(createTableCell("Available", true))
        materialsTable.addView(headerRow)
    }

    private fun addEmptyStateRow(text: String) {

        val row = TableRow(this)
        row.addView(createTableCell(text, false, span = 3))
        materialsTable.addView(row)
    }

    private fun addMaterialTableRow(
        materialName: String,
        requiredText: String,
        availableText: String,
        isEnough: Boolean
    ) {

        val row = TableRow(this)
        row.addView(createTableCell(materialName))
        row.addView(createTableCell(requiredText))
        row.addView(
            createTableCell(
                availableText,
                textColor = if (isEnough) {
                    resources.getColor(R.color.brand_success, theme)
                } else {
                    resources.getColor(R.color.brand_accent, theme)
                }
            )
        )
        materialsTable.addView(row)
    }

    private fun createTableCell(
        text: String,
        isHeader: Boolean = false,
        textColor: Int = resources.getColor(R.color.brand_ink, theme),
        span: Int = 1
    ): TextView {

        return TextView(this).apply {
            this.text = text
            setTextColor(textColor)
            textSize = if (isHeader) 13f else 14f
            setTypeface(typeface, if (isHeader) Typeface.BOLD else Typeface.NORMAL)
            setPadding(8, 10, 8, 10)
            layoutParams = TableRow.LayoutParams(0, TableRow.LayoutParams.WRAP_CONTENT, 1f).apply {
                this.span = span
            }
        }
    }

    private fun formatNumber(value: Float): String {
        return if (value % 1f == 0f) {
            value.toInt().toString()
        } else {
            String.format(Locale.US, "%.2f", value)
        }
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
