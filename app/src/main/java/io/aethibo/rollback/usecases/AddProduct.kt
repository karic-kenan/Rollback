package io.aethibo.rollback.usecases

import io.aethibo.rollback.data.repositories.MainRepository
import io.aethibo.rollback.domain.mapped.ProductItem
import io.aethibo.rollback.domain.request.AddProductRequest
import io.aethibo.rollback.framework.utils.Resource

interface AddProductUseCase {
    suspend operator fun invoke(data: AddProductRequest): Resource<ProductItem>
}

class AddProductUseCaseImpl(private val repository: MainRepository) : AddProductUseCase {

    override suspend operator fun invoke(data: AddProductRequest) =
        repository.addProduct(data)
}