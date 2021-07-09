package io.aethibo.rollback.data.remote.api

import io.aethibo.rollback.domain.request.AddProductRequest
import io.aethibo.rollback.domain.request.UserRequest
import io.aethibo.rollback.domain.response.LoginResponse
import io.aethibo.rollback.domain.response.ProductItemResponse
import io.aethibo.rollback.domain.response.UserItemResponse
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface ApiService {

    @POST("auth/login")
    suspend fun loginUser(@Body data: UserRequest): LoginResponse

    @GET("users")
    suspend fun getUsers(): List<UserItemResponse>

    @GET("users/{id}")
    suspend fun getUser(@Path("id") id: String): UserItemResponse

    @GET("products")
    suspend fun getProducts(): List<ProductItemResponse>

    @GET("products/{id}")
    suspend fun getProduct(@Path("id") id: Int): ProductItemResponse

    @POST("products")
    suspend fun addProduct(@Body data: AddProductRequest): ProductItemResponse
}