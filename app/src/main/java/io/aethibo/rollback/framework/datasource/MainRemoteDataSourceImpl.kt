package io.aethibo.rollback.framework.datasource

import io.aethibo.data.datasource.MainRemoteDataSource
import io.aethibo.data.mapper.ProductMapper
import io.aethibo.data.mapper.UserMapper
import io.aethibo.data.api.ApiService
import io.aethibo.data.utils.Resource
import io.aethibo.domain.mapped.ProductItem
import io.aethibo.domain.mapped.UserItem
import io.aethibo.domain.response.LoginResponse
import io.aethibo.data.utils.safeCall
import io.aethibo.domain.response.ProductItemResponse
import io.aethibo.domain.response.UserItemResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class MainRemoteDataSourceImpl(
    private val apiService: ApiService,
    private val userMapper: UserMapper,
    private val productMapper: ProductMapper
) : MainRemoteDataSource {

    override suspend fun loginUser(user: io.aethibo.domain.request.UserRequest): Resource<LoginResponse> =
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

    override suspend fun getCategories(): Resource<List<String>> = withContext(Dispatchers.IO) {
        safeCall {
            val response: List<String> = apiService.getCategories()

            Resource.Success(response)
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

    override suspend fun addProduct(data: io.aethibo.domain.request.AddProductRequest): Resource<ProductItem> =
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
                /**
                 * This endpoint returns the deleted product,
                 * but we'll return custom value
                 * boolean in case of product deletion
                 */

                Resource.Success(true)
            }
        }
}