package io.aethibo.rollback.features.login

import io.aethibo.rollback.domain.request.UserRequest
import io.aethibo.rollback.framework.mvibase.IIntent

sealed class LoginIntent: IIntent {
    object GetUsers : LoginIntent()
    data class LoginUser(val user: UserRequest) : LoginIntent()
}