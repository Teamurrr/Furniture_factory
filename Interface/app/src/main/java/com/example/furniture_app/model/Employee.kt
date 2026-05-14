package com.example.furniture_app.model

import com.google.gson.annotations.SerializedName

data class Employee(

    val id: Int,

    @SerializedName("full_name")
    val fullName: String
)