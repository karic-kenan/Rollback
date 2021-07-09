package io.aethibo.rollback.features.add.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import io.aethibo.rollback.domain.mapped.ProductItem
import io.aethibo.rollback.domain.request.AddProductRequest
import io.aethibo.rollback.features.add.AddProductIntent
import io.aethibo.rollback.features.add.AddProductIntent.AddProduct
import io.aethibo.rollback.features.add.AddProductState
import io.aethibo.rollback.framework.mvibase.IModel
import io.aethibo.rollback.framework.utils.Resource
import io.aethibo.rollback.framework.utils.Resource.Failure
import io.aethibo.rollback.framework.utils.Resource.Success
import io.aethibo.rollback.usecases.AddProductUseCase
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.consumeAsFlow
import kotlinx.coroutines.launch

class AddViewModel(private val addProduct: AddProductUseCase) : ViewModel(),
    IModel<AddProductState, AddProductIntent> {
    override val intents: Channel<AddProductIntent> by lazy { Channel(Channel.BUFFERED) }

    private val _state: MutableStateFlow<AddProductState> = MutableStateFlow(AddProductState())
    override val state: StateFlow<AddProductState>
        get() = _state

    init {
        handleIntents()
    }

    private fun handleIntents() {
        viewModelScope.launch {
            intents.consumeAsFlow().collect { userIntents ->
                when (userIntents) {
                    is AddProduct -> addProduct(userIntents.product)
                }
            }
        }
    }

    fun addProduct(product: AddProductRequest) {
        viewModelScope.launch {
            updateState { it.copy(isLoading = true) }

            val response: Resource<ProductItem> = addProduct.invoke(product)

            when (response) {
                is Success -> updateState {
                    it.copy(
                        isLoading = false,
                        product = response.data!!
                    )
                }
                is Failure -> updateState {
                    it.copy(
                        isLoading = false,
                        errorMessage = response.message
                    )
                }
            }
        }
    }

    private suspend fun updateState(handler: suspend (intent: AddProductState) -> AddProductState) {
        _state.value = handler(state.value)
    }
}