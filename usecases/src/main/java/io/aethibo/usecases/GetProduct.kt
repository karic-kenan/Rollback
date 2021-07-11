package io.aethibo.usecases

import io.aethibo.data.repositories.MainRepository
import io.aethibo.data.utils.Resource
import io.aethibo.domain.mapped.ProductItem

interface GetProductUseCase {
    suspend operator fun invoke(id: Int): Resource<ProductItem>
}

class GetProductUseCaseImpl(private val repository: MainRepository) : GetProductUseCase {

    override suspend operator fun invoke(id: Int): Resource<ProductItem> =
        repository.getProduct(id)
}