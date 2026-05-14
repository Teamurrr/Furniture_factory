package com.example.furniture_app.ui

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.furniture_app.R
import com.example.furniture_app.model.RawMaterial

class RawMaterialAdapter(
    private val materials: List<RawMaterial>
) : RecyclerView.Adapter<RawMaterialAdapter.ViewHolder>() {

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        val name: TextView = view.findViewById(R.id.materialName)
        val quantity: TextView = view.findViewById(R.id.materialQuantity)
        val amount: TextView = view.findViewById(R.id.materialAmount)
        val unit: TextView = view.findViewById(R.id.materialUnit)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_material, parent, false)

        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        val material = materials[position]

        holder.name.text = material.name

        holder.quantity.text =
            "Quantity: ${material.quantity}"

        holder.amount.text =
            "Price: ${material.amount}"

        holder.unit.text =
            "Unit: ${material.unitOfMeasure?.name}"

    }

    override fun getItemCount(): Int {
        return materials.size
    }
}