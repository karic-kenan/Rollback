package io.aethibo.rollback.usecases

import io.aethibo.rollback.data.repositories.MainRepository
import io.aethibo.rollback.domain.mapped.UserItem
import io.aethibo.rollback.framework.utils.Resource

interface GetUsersUseCase {
    suspend operator fun invoke(): Resource<List<UserItem>>
}

class GetUsersUseCaseImpl(private val repository: MainRepository) : GetUsersUseCase {

    override suspend fun invoke(): Resource<List<UserItem>> =
        repository.getUsers()
}