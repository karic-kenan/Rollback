package io.aethibo.rollback.features.add

import io.aethibo.domain.mapped.ProductItem
import io.aethibo.rollback.framework.mvibase.IState

data class AddProductState(
    val isLoading: Boolean = false,
    val product: ProductItem? = null,
    val categories: List<String> = emptyList(),
    val errorMessage: String? = null
) : IState