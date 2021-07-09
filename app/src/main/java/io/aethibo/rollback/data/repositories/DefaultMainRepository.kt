package io.aethibo.rollback.data.repositories

import io.aethibo.rollback.data.datasource.MainRemoteDataSource
import io.aethibo.rollback.domain.mapped.ProductItem
import io.aethibo.rollback.domain.mapped.UserItem
import io.aethibo.rollback.domain.request.AddProductRequest
import io.aethibo.rollback.domain.request.UserRequest
import io.aethibo.rollback.domain.response.LoginResponse
import io.aethibo.rollback.framework.utils.Resource

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