package io.aethibo.usecases

import io.aethibo.data.repositories.MainRepository
import io.aethibo.data.utils.Resource

interface DeleteProductUseCase {
    suspend operator fun invoke(id: Int): Resource<Boolean>
}

class DeleteProductUseCaseImpl(private val repository: MainRepository) : DeleteProductUseCase {

    override suspend fun invoke(id: Int): Resource<Boolean> =
        repository.deleteProduct(id)
}