package com.example.furniture_app.ui

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.furniture_app.R
import java.util.Locale

open class ReportAdapter(private val data: List<Map<String, Any>>) :
    RecyclerView.Adapter<ReportAdapter.ViewHolder>() {

    private val columns: List<String> = data.firstOrNull()
        ?.keys
        ?.filterNot(::isIdColumn)
        ?.toList()
        ?: emptyList()
    private var filteredData: List<Map<String, Any>> = data

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val rowContainer: LinearLayout = itemView.findViewById(R.id.rowContainer)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_report, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int = filteredData.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val context = holder.itemView.context
        val item = filteredData[position]
        val isEvenRow = position % 2 == 0

        holder.rowContainer.removeAllViews()
        holder.itemView.setBackgroundColor(
            ContextCompat.getColor(
                context,
                if (isEvenRow) android.R.color.white else android.R.color.darker_gray
            )
        )

        columns.forEach { column ->
            val cell = TextView(context).apply {
                layoutParams = LinearLayout.LayoutParams(
                    0,
                    ViewGroup.LayoutParams.WRAP_CONTENT,
                    1f
                )
                setPadding(24, 20, 24, 20)
                text = formatValue(column, item[column])
                setTextColor(ContextCompat.getColor(context, android.R.color.black))
                textSize = 14f
                maxLines = 3
            }
            holder.rowContainer.addView(cell)
        }
    }

    fun getColumns(): List<String> = columns

    fun getFormattedColumns(): List<String> = columns.map(::formatHeader)

    fun filter(query: String) {
        val normalizedQuery = query.trim().lowercase(Locale.getDefault())
        filteredData = if (normalizedQuery.isEmpty()) {
            data
        } else {
            data.filter { row ->
                columns.any { column ->
                    formatValue(column, row[column])
                        .lowercase(Locale.getDefault())
                        .contains(normalizedQuery)
                }
            }
        }
        notifyDataSetChanged()
    }

    private fun formatValue(column: String, value: Any?): String {
        val text = value?.toString() ?: return "-"

        return if (
            column.equals("PaymentDate", ignoreCase = true) ||
            column.equals("payment_date", ignoreCase = true) ||
            column.equals("Date", ignoreCase = true) ||
            column.equals("sale_date", ignoreCase = true)
        ) {
            text.take(10)
        } else {
            text
        }
    }

    private fun formatHeader(key: String): String {
        return key
            .split("_")
            .joinToString(" ") { part ->
                part.lowercase(Locale.getDefault()).replaceFirstChar { char ->
                    if (char.isLowerCase()) char.titlecase(Locale.getDefault()) else char.toString()
                }
            }
    }

    private fun isIdColumn(key: String): Boolean {
        val normalized = key.lowercase(Locale.getDefault())
        return normalized == "id" || normalized.endsWith("_id")
    }
}
