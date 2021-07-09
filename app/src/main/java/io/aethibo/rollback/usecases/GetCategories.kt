package io.aethibo.rollback.usecases

import io.aethibo.rollback.data.repositories.MainRepository
import io.aethibo.rollback.framework.utils.Resource

interface GetCategoriesUseCase {
    suspend operator fun invoke(): Resource<List<String>>
}

class GetCategoriesUseCaseImpl(private val repository: MainRepository) : GetCategoriesUseCase {

    override suspend operator fun invoke() = repository.getCategories()
}