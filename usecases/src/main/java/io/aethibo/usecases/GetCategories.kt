package io.aethibo.usecases

import io.aethibo.data.repositories.MainRepository
import io.aethibo.data.utils.Resource

interface GetCategoriesUseCase {
    suspend operator fun invoke(): Resource<List<String>>
}

class GetCategoriesUseCaseImpl(private val repository: MainRepository) : GetCategoriesUseCase {

    override suspend operator fun invoke(): Resource<List<String>> = repository.getCategories()
}