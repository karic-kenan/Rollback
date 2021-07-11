package io.aethibo.usecases

import io.aethibo.data.repositories.MainRepository
import io.aethibo.data.utils.Resource
import io.aethibo.domain.request.UserRequest
import io.aethibo.domain.response.LoginResponse

interface LoginUserUseCase {
    suspend operator fun invoke(user: UserRequest): Resource<LoginResponse>
}

class LoginUserUseCaseImpl(private val repository: MainRepository) : LoginUserUseCase {

    override suspend fun invoke(user: UserRequest): Resource<LoginResponse> =
        repository.loginUser(user)
}