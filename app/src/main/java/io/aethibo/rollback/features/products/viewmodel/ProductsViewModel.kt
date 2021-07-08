package io.aethibo.rollback.features.products.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import io.aethibo.rollback.features.products.ProductsIntent
import io.aethibo.rollback.features.products.ProductsState
import io.aethibo.rollback.framework.mvibase.IModel
import io.aethibo.rollback.framework.utils.Resource
import io.aethibo.rollback.usecases.GetProductsUseCase
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.consumeAsFlow
import kotlinx.coroutines.launch

class ProductsViewModel(private val getProducts: GetProductsUseCase) : ViewModel(),
    IModel<ProductsState, ProductsIntent> {

    override val intents: Channel<ProductsIntent> by lazy { Channel(Channel.BUFFERED) }

    private val _state: MutableStateFlow<ProductsState> = MutableStateFlow(ProductsState())
    override val state: StateFlow<ProductsState>
        get() = _state

    init {
        handleIntents()
    }

    private fun handleIntents() {
        viewModelScope.launch {
            intents.consumeAsFlow().collect { userIntents ->
                when (userIntents) {
                    ProductsIntent.GetProducts -> getProducts()
                }
            }
        }
    }

    private fun getProducts() {
        viewModelScope.launch {
            updateState { it.copy(isLoading = true) }

            val response = getProducts.invoke()

            when (response) {
                is Resource.Success -> updateState {
                    it.copy(
                        isLoading = false,
                        products = response.data!!
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

    private suspend fun updateState(handler: suspend (intent: ProductsState) -> ProductsState) {
        _state.value = handler(state.value)
    }
}