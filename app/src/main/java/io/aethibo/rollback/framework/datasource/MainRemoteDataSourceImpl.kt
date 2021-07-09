package io.aethibo.rollback.framework.datasource

import io.aethibo.rollback.data.datasource.MainRemoteDataSource
import io.aethibo.rollback.data.mapper.ProductMapper
import io.aethibo.rollback.data.mapper.UserMapper
import io.aethibo.rollback.data.remote.api.ApiService
import io.aethibo.rollback.domain.mapped.ProductItem
import io.aethibo.rollback.domain.mapped.UserItem
import io.aethibo.rollback.domain.request.AddProductRequest
import io.aethibo.rollback.domain.request.UserRequest
import io.aethibo.rollback.domain.response.LoginResponse
import io.aethibo.rollback.domain.response.ProductItemResponse
import io.aethibo.rollback.domain.response.UserItemResponse
import io.aethibo.rollback.framework.utils.Resource
import io.aethibo.rollback.framework.utils.safeCall
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class MainRemoteDataSourceImpl(
    private val apiService: ApiService,
    private val userMapper: UserMapper,
    private val productMapper: ProductMapper
) : MainRemoteDataSource {

    override suspend fun loginUser(user: UserRequest): Resource<LoginResponse> =
        withContext(Dispatchers.IO) {
            safeCall {
                val response: LoginResponse = apiService.loginUser(user)

                Resource.Success(response)
            }
        }

    override suspend fun getUsers(): Resource<List<UserItem>> =
        withContext(Dispatchers.IO) {
            safeCall {
                val response: List<UserItemResponse> = apiService.getUsers()
                val mappedValues: List<UserItem> = userMapper.mapFromEntityList(response)

                Resource.Success(mappedValues)
            }
        }

    override suspend fun getUser(id: String): Resource<UserItem> = withContext(Dispatchers.IO) {
        safeCall {
            val response: UserItemResponse = apiService.getUser(id)
            val mappedValues: UserItem = userMapper.mapFromEntity(response)

            Resource.Success(mappedValues)
        }
    }

    override suspend fun getProducts(): Resource<List<ProductItem>> =
        withContext(Dispatchers.IO) {
            safeCall {
                val response: List<ProductItemResponse> = apiService.getProducts()
                val mappedValues: List<ProductItem> = productMapper.mapFromEntityList(response)

                Resource.Success(mappedValues)
            }
        }

    override suspend fun getProduct(id: Int): Resource<ProductItem> =
        withContext(Dispatchers.IO) {
            safeCall {
                val response: ProductItemResponse = apiService.getProduct(id)
                val mappedValues: ProductItem = productMapper.mapFromEntity(response)

                Resource.Success(mappedValues)
            }
        }

    override suspend fun addProduct(data: AddProductRequest): Resource<ProductItem> =
        withContext(Dispatchers.IO) {
            safeCall {
                val response: ProductItemResponse = apiService.addProduct(data)
                val mappedValues: ProductItem = productMapper.mapFromEntity(response)

                Resource.Success(mappedValues)
            }
        }

    override suspend fun deleteProduct(id: Int): Resource<Boolean> =
        withContext(Dispatchers.IO) {
            safeCall {
                val response: ProductItemResponse = apiService.deleteProduct(id)
                /**
                 * This endpoint returns the deleted product,
                 * but we'll return custom value
                 * boolean in case of product deletion
                 */

                Resource.Success(true)
            }
        }
}