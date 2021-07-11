package io.aethibo.data.repositories

import io.aethibo.data.datasource.MainRemoteDataSource
import io.aethibo.data.utils.Resource
import io.aethibo.domain.mapped.ProductItem
import io.aethibo.domain.mapped.UserItem
import io.aethibo.domain.request.AddProductRequest
import io.aethibo.domain.request.UserRequest
import io.aethibo.domain.response.LoginResponse

class DefaultMainRepository(private val remoteAccess: MainRemoteDataSource) : MainRepository {

    override suspend fun loginUser(user: UserRequest): Resource<LoginResponse> =
        remoteAccess.loginUser(user)

    override suspend fun getUsers(): Resource<List<UserItem>> =
        remoteAccess.getUsers()

    override suspend fun getUser(id: String): Resource<UserItem> =
        remoteAccess.getUser(id)

    override suspend fun getCategories(): Resource<List<String>> =
        remoteAccess.getCategories()

    override suspend fun getProducts(): Resource<List<ProductItem>> =
        remoteAccess.getProducts()

    override suspend fun getProduct(id: Int): Resource<ProductItem> =
        remoteAccess.getProduct(id)

    override suspend fun addProduct(data: AddProductRequest): Resource<ProductItem> =
        remoteAccess.addProduct(data)

    override suspend fun deleteProduct(id: Int): Resource<Boolean> =
        remoteAccess.deleteProduct(id)
}