package io.aethibo.rollback.data.repositories

import io.aethibo.rollback.domain.mapped.ProductItem
import io.aethibo.rollback.domain.mapped.UserItem
import io.aethibo.rollback.domain.request.AddProductRequest
import io.aethibo.rollback.domain.request.UserRequest
import io.aethibo.rollback.domain.response.LoginResponse
import io.aethibo.rollback.framework.utils.Resource

interface MainRepository {
    // Auth
    suspend fun loginUser(user: UserRequest): Resource<LoginResponse>

    // User handler
    suspend fun getUsers(): Resource<List<UserItem>>
    suspend fun getUser(id: String): Resource<UserItem>

    // Product handler
    suspend fun getProducts(): Resource<List<ProductItem>>
    suspend fun getProduct(id: Int): Resource<ProductItem>
    suspend fun addProduct(data: AddProductRequest): Resource<ProductItem>
    suspend fun deleteProduct(id: Int): Resource<Boolean>
}