package io.aethibo.rollback.usecases

import io.aethibo.rollback.data.repositories.MainRepository
import io.aethibo.rollback.domain.request.UserRequest
import io.aethibo.rollback.domain.response.LoginResponse
import io.aethibo.rollback.framework.utils.Resource

interface LoginUserUseCase {
    suspend operator fun invoke(user: UserRequest): Resource<LoginResponse>
}

class LoginUserUseCaseImpl(private val repository: MainRepository) : LoginUserUseCase {

    override suspend fun invoke(user: UserRequest): Resource<LoginResponse> =
        repository.loginUser(user)
}