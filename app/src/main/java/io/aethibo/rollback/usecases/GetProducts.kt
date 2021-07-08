package io.aethibo.rollback.usecases

import io.aethibo.rollback.data.repositories.MainRepository
import io.aethibo.rollback.domain.mapped.ProductItem
import io.aethibo.rollback.framework.utils.Resource

interface GetProductsUseCase {
    suspend operator fun invoke(): Resource<List<ProductItem>>
}

class GetProductsUseCaseImpl(private val repository: MainRepository) : GetProductsUseCase {

    override suspend fun invoke(): Resource<List<ProductItem>> =
        repository.getProducts()
}