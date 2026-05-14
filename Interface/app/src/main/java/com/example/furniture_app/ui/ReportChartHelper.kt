package com.example.furniture_app.ui

import android.content.Context
import android.graphics.Color
import android.view.Gravity
import android.view.View
import android.widget.LinearLayout
import android.widget.TextView
import kotlin.math.max

object ReportChartHelper {

    private val labelCandidates = listOf(
        "date",
        "payment_date",
        "paymentdate",
        "credit_date",
        "product_name",
        "raw_material_name",
        "employee_name",
        "name"
    )

    private val valueCandidates = listOf(
        "amount",
        "total_to_pay",
        "credit_amount",
        "quantity",
        "salary",
        "payment_amount"
    )

    fun bind(container: LinearLayout, rows: List<Map<String, Any>>) {
        val config = buildConfig(rows)
        container.removeAllViews()

        if (config == null) {
            container.visibility = View.GONE
            return
        }

        container.visibility = View.VISIBLE

        config.items.forEach { item ->
            container.addView(createBarItem(container.context, item, config.maxValue))
        }
    }

    private fun createBarItem(
        context: Context,
        item: ChartItem,
        maxValue: Float
    ): LinearLayout {
        val root = LinearLayout(context).apply {
            orientation = LinearLayout.VERTICAL
            gravity = Gravity.BOTTOM or Gravity.CENTER_HORIZONTAL
            layoutParams = LinearLayout.LayoutParams(dp(context, 72), LinearLayout.LayoutParams.MATCH_PARENT).apply {
                marginEnd = dp(context, 8)
            }
        }

        val valueText = TextView(context).apply {
            text = formatNumber(item.value)
            setTextColor(Color.BLACK)
            textSize = 12f
            gravity = Gravity.CENTER
        }

        val barHeight = max(dp(context, 24), ((item.value / maxValue) * dp(context, 120)).toInt())
        val bar = View(context).apply {
            layoutParams = LinearLayout.LayoutParams(dp(context, 36), barHeight).apply {
                topMargin = dp(context, 6)
                bottomMargin = dp(context, 6)
            }
            setBackgroundColor(Color.parseColor("#4F81BD"))
        }

        val labelText = TextView(context).apply {
            text = item.label
            setTextColor(Color.DKGRAY)
            textSize = 12f
            gravity = Gravity.CENTER
            maxLines = 2
        }

        root.addView(valueText)
        root.addView(bar)
        root.addView(labelText)

        return root
    }

    private fun buildConfig(rows: List<Map<String, Any>>): ChartConfig? {
        if (rows.isEmpty()) return null

        val keys = rows.first().keys.toList()
        val labelKey = keys.firstOrNull { matchesCandidate(it, labelCandidates) } ?: return null
        val valueKey = keys.firstOrNull { matchesCandidate(it, valueCandidates) } ?: return null

        val items = rows.take(8).mapNotNull { row ->
            val label = row[labelKey]?.toString()?.let(::normalizeLabel) ?: return@mapNotNull null
            val value = row[valueKey]?.toString()?.toFloatOrNull() ?: return@mapNotNull null
            ChartItem(label, value)
        }

        if (items.isEmpty()) return null

        return ChartConfig(
            items = items,
            maxValue = items.maxOf { it.value }.coerceAtLeast(1f)
        )
    }

    private fun matchesCandidate(key: String, candidates: List<String>): Boolean {
        val normalized = key.lowercase().replace("_", "")
        return candidates.any { normalized == it.lowercase().replace("_", "") }
    }

    private fun normalizeLabel(value: String): String {
        return if (value.length >= 10 && value[4] == '-' && value[7] == '-') {
            value.take(10)
        } else {
            value.take(12)
        }
    }

    private fun formatNumber(value: Float): String {
        return if (value % 1f == 0f) value.toInt().toString() else String.format("%.1f", value)
    }

    private fun dp(context: Context, value: Int): Int {
        return (value * context.resources.displayMetrics.density).toInt()
    }

    private data class ChartConfig(
        val items: List<ChartItem>,
        val maxValue: Float
    )

    private data class ChartItem(
        val label: String,
        val value: Float
    )
}
