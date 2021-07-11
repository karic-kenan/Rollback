package io.aethibo.usecases

import io.aethibo.data.repositories.MainRepository
import io.aethibo.data.utils.Resource
import io.aethibo.domain.mapped.ProductItem
import io.aethibo.domain.request.AddProductRequest

interface AddProductUseCase {
    suspend operator fun invoke(data: AddProductRequest): Resource<ProductItem>
}

class AddProductUseCaseImpl(private val repository: MainRepository) : AddProductUseCase {

    override suspend operator fun invoke(data: AddProductRequest) =
        repository.addProduct(data)
}