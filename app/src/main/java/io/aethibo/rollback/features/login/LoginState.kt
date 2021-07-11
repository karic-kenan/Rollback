package io.aethibo.rollback.features.login

import io.aethibo.domain.mapped.UserItem
import io.aethibo.rollback.framework.mvibase.IState

data class LoginState(
    val isLoading: Boolean = false,
    val users: List<UserItem> = emptyList(),
    val token: String? = null,
    val errorMessage: String? = null
) : IState