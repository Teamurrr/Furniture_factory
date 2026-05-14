package com.example.furniture_app.api

import com.example.furniture_app.model.Employee
import com.example.furniture_app.model.FinishedProduct
import com.example.furniture_app.model.ProductMaterialRequirement
import com.example.furniture_app.model.ProductProduction
import com.example.furniture_app.model.ProductSale
import com.example.furniture_app.model.PurchaseRequest
import com.example.furniture_app.model.RawMaterial
import retrofit2.Call
import retrofit2.http.*


interface ApiService {

    @GET("rawmaterials")
    fun getRawMaterials(): Call<List<RawMaterial>>

    @GET("employees")
    fun getEmployees(): Call<List<Employee>>

    @GET("rawmaterials/{id}")
    fun getRawMaterialById(@Path("id") id: Int): Call<RawMaterial>

    @POST("rawmaterials")
    fun createRawMaterial(@Body rawMaterial: RawMaterial): Call<RawMaterial>

    @DELETE("rawmaterials/{id}")
    fun deleteRawMaterial(@Path("id") id: Int): Call<Void>

    @POST("rawmaterialpurchase")
    fun purchaseRawMaterial(
        @Body purchase: PurchaseRequest
    ): Call<Void>


    @POST("production")
    fun produceProduct(
        @Body production: ProductProduction
    ): Call<Void>

    @GET("finishedproducts")
    fun getProducts(): Call<List<FinishedProduct>>

    @GET("finishedproducts")
    fun getFinishedProducts(): Call<List<FinishedProduct>>

    @GET("finishedproducts/{id}/materials")
    fun getProductMaterials(
        @Path("id") id: Int
    ): Call<List<ProductMaterialRequirement>>


    @POST("productsales")
    fun createSale(@Body sale: ProductSale): Call<ProductSale>

    @POST("salary")
    fun paySalary(
        @Query("employee_id") employeeId: Int,
        @Query("amount") amount: Double
    ): Call<String>

    @POST("credit")
    fun takeCredit(
        @Query("amount") amount: Double
    ): Call<Int>



    @GET("reports/purchases")
    fun getPurchasesReport(): Call<List<Map<String, Any>>>

    @GET("reports/sales")
    fun getSalesReport(): Call<List<Map<String, Any>>>

    @GET("reports/production")
    fun getProductionReport(): Call<List<Map<String, Any>>>

    @GET("reports/salary")
    fun getSalaryReport(): Call<List<Map<String, Any>>>

    @GET("reports/credits")
    fun getCreditsReport(): Call<List<Map<String, Any>>>


}
