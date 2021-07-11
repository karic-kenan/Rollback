package io.aethibo.usecases

import io.aethibo.data.repositories.MainRepository
import io.aethibo.data.utils.Resource
import io.aethibo.domain.mapped.UserItem

interface GetUsersUseCase {
    suspend operator fun invoke(): Resource<List<UserItem>>
}

class GetUsersUseCaseImpl(private val repository: MainRepository) : GetUsersUseCase {

    override suspend fun invoke(): Resource<List<UserItem>> =
        repository.getUsers()
}