package io.aethibo.rollback.data.repositories

import io.aethibo.rollback.domain.mapped.UserItem
import io.aethibo.rollback.domain.request.UserRequest
import io.aethibo.rollback.domain.response.LoginResponse
import io.aethibo.rollback.framework.utils.Resource

interface MainRepository {
    // Auth
    suspend fun loginUser(user: UserRequest): Resource<LoginResponse>

    // User handler
    suspend fun getUsers(): Resource<List<UserItem>>
    suspend fun getUser(id: String): Resource<UserItem>
}