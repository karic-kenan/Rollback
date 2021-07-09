package io.aethibo.rollback.features.detail.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import io.aethibo.rollback.domain.mapped.ProductItem
import io.aethibo.rollback.features.detail.DetailIntent
import io.aethibo.rollback.features.detail.DetailState
import io.aethibo.rollback.framework.mvibase.IModel
import io.aethibo.rollback.framework.utils.Resource
import io.aethibo.rollback.usecases.DeleteProductUseCase
import io.aethibo.rollback.usecases.GetProductUseCase
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.consumeAsFlow
import kotlinx.coroutines.launch

class DetailViewModel(
    private val getProduct: GetProductUseCase,
    private val deleteProduct: DeleteProductUseCase
) : ViewModel(),
    IModel<DetailState, DetailIntent> {

    override val intents: Channel<DetailIntent> by lazy { Channel(Channel.BUFFERED) }

    private val _state: MutableStateFlow<DetailState> = MutableStateFlow(DetailState())
    override val state: StateFlow<DetailState>
        get() = _state

    init {
        handleIntents()
    }

    private fun handleIntents() {
        viewModelScope.launch {
            intents.consumeAsFlow().collect { userIntents ->
                when (userIntents) {
                    is DetailIntent.GetProduct -> fetchProduct(userIntents.id)
                    is DetailIntent.DeleteProduct -> deleteProduct(userIntents.id)
                }
            }
        }
    }

    private fun fetchProduct(id: Int) {
        viewModelScope.launch {
            updateState { it.copy(isLoading = true) }

            val response: Resource<ProductItem> = getProduct.invoke(id)

            when (response) {
                is Resource.Success -> updateState {
                    it.copy(
                        isLoading = false,
                        product = response.data!!
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

    private fun deleteProduct(id: Int) {
        viewModelScope.launch {
            updateState { it.copy(isLoading = true) }

            val response: Resource<Boolean> = deleteProduct.invoke(id)

            when (response) {
                is Resource.Success -> updateState {
                    it.copy(
                        isLoading = false,
                        isProductDeleted = response.data!!
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

    private suspend fun updateState(handler: suspend (intent: DetailState) -> DetailState) {
        _state.value = handler(state.value)
    }
}