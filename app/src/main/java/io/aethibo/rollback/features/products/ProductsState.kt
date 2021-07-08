package io.aethibo.rollback.features.products

import io.aethibo.rollback.domain.mapped.ProductItem
import io.aethibo.rollback.framework.mvibase.IState

data class ProductsState(
    val isLoading: Boolean = false,
    val products: List<ProductItem> = emptyList(),
    val errorMessage: String? = null
) : IState