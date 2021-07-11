package io.aethibo.usecases

import io.aethibo.data.repositories.MainRepository
import io.aethibo.data.utils.Resource
import io.aethibo.domain.mapped.ProductItem

interface GetProductsUseCase {
    suspend operator fun invoke(): Resource<List<ProductItem>>
}

class GetProductsUseCaseImpl(private val repository: MainRepository) : GetProductsUseCase {

    override suspend fun invoke(): Resource<List<ProductItem>> =
        repository.getProducts()
}