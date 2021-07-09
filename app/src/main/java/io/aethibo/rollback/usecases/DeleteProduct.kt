package io.aethibo.rollback.usecases

import io.aethibo.rollback.data.repositories.MainRepository
import io.aethibo.rollback.framework.utils.Resource

interface DeleteProductUseCase {
    suspend operator fun invoke(id: Int): Resource<Boolean>
}

class DeleteProductUseCaseImpl(private val repository: MainRepository) : DeleteProductUseCase {

    override suspend fun invoke(id: Int): Resource<Boolean> =
        repository.deleteProduct(id)
}