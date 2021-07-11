package io.aethibo.rollback.features.products

import io.aethibo.domain.mapped.ProductItem
import io.aethibo.rollback.framework.mvibase.IState

data class ProductsState(
    val isLoading: Boolean = false,
    val products: List<io.aethibo.domain.mapped.ProductItem> = emptyList(),
    val errorMessage: String? = null
) : IState