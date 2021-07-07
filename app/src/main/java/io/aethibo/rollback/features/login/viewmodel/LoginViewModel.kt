package io.aethibo.rollback.features.login.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import io.aethibo.rollback.domain.mapped.UserItem
import io.aethibo.rollback.domain.request.UserRequest
import io.aethibo.rollback.features.login.LoginIntent
import io.aethibo.rollback.features.login.LoginState
import io.aethibo.rollback.framework.mvibase.IModel
import io.aethibo.rollback.framework.utils.Resource
import io.aethibo.rollback.usecases.GetUsersUseCase
import io.aethibo.rollback.usecases.LoginUserUseCase
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.consumeAsFlow
import kotlinx.coroutines.launch

class LoginViewModel(private val login: LoginUserUseCase, private val getUsers: GetUsersUseCase) :
    ViewModel(), IModel<LoginState, LoginIntent> {

    override val intents: Channel<LoginIntent> = Channel(Channel.BUFFERED)

    private val _state: MutableStateFlow<LoginState> = MutableStateFlow(LoginState())
    override val state: StateFlow<LoginState>
        get() = _state

    init {
        handleIntents()
    }

    private fun handleIntents() {
        viewModelScope.launch {
            intents.consumeAsFlow().collect { userIntent ->
                when (userIntent) {
                    is LoginIntent.GetUsers -> getUsers()
                    is LoginIntent.LoginUser -> loginUser(userIntent.user)
                }
            }
        }
    }

    private fun loginUser(userRequest: UserRequest) {
        viewModelScope.launch {
            updateState { it.copy(isLoading = true) }

            val response = login.invoke(userRequest)

            when (response) {
                is Resource.Success -> updateState {
                    it.copy(
                        isLoading = false,
                        token = response.data?.token!!
                    )
                }
                is Resource.Failure -> updateState {
                    it.copy(
                        isLoading = false,
                        errorMessage = response.message
                    )
                }
            }
        }
    }

    private fun getUsers() {
        viewModelScope.launch {
            updateState { it.copy(isLoading = true) }

            val response: Resource<List<UserItem>> = getUsers.invoke()

            when (response) {
                is Resource.Success -> updateState {
                    it.copy(
                        isLoading = false,
                        users = response.data!!
                    )
                }
                is Resource.Failure -> updateState {
                    it.copy(
                        isLoading = false,
                        errorMessage = response.message
                    )
                }
            }
        }
    }

    private suspend fun updateState(handler: suspend (intent: LoginState) -> LoginState) {
        _state.value = handler(state.value)
    }
}