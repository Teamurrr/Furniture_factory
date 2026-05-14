package com.example.furniture_app.ui

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.furniture_app.R
import com.example.furniture_app.api.RetrofitClient
import com.example.furniture_app.model.RawMaterial
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.furniture_app.api.ApiService
import com.example.furniture_app.ui.RawMaterialAdapter


class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)

        val recyclerView = findViewById<RecyclerView>(R.id.MaterialRecyclerView)

        val api = RetrofitClient.apiService
        api.getRawMaterials().enqueue(object : Callback<List<RawMaterial>> {

            override fun onResponse(
                call: Call<List<RawMaterial>>,
                response: Response<List<RawMaterial>>
            ) {

                val materials = response.body()

                if (materials != null) {

                    recyclerView.layoutManager = LinearLayoutManager(this@MainActivity)
                    recyclerView.adapter = RawMaterialAdapter(materials)

                }
            }

            override fun onFailure(call: Call<List<RawMaterial>>, t: Throwable) {
                t.printStackTrace()
            }

        })


    }


}