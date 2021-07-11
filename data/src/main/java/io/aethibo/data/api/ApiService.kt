package io.aethibo.data.api

import io.aethibo.domain.request.AddProductRequest
import io.aethibo.domain.request.UserRequest
import io.aethibo.domain.response.LoginResponse
import io.aethibo.domain.response.ProductItemResponse
import io.aethibo.domain.response.UserItemResponse
import retrofit2.http.*

interface ApiService {

    @POST("auth/login")
    suspend fun loginUser(@Body data: UserRequest): LoginResponse

    @GET("users")
    suspend fun getUsers(): List<UserItemResponse>

    @GET("users/{id}")
    suspend fun getUser(@Path("id") id: String): UserItemResponse

    @GET("products/categories")
    suspend fun getCategories(): List<String>

    @GET("products")
    suspend fun getProducts(): List<ProductItemResponse>

    @GET("products/{id}")
    suspend fun getProduct(@Path("id") id: Int): ProductItemResponse

    @POST("products")
    suspend fun addProduct(@Body data: AddProductRequest): ProductItemResponse

    @DELETE("products/{id}")
    suspend fun deleteProduct(@Path("id") id: Int): ProductItemResponse
}