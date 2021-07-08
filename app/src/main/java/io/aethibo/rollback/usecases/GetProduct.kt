package io.aethibo.rollback.usecases

import io.aethibo.rollback.data.repositories.MainRepository
import io.aethibo.rollback.domain.mapped.ProductItem
import io.aethibo.rollback.framework.utils.Resource

interface GetProductUseCase {
    suspend operator fun invoke(id: String): Resource<ProductItem>
}

class GetProductUseCaseImpl(private val repository: MainRepository) : GetProductUseCase {

    override suspend operator fun invoke(id: String) =
        repository.getProduct(id)
}