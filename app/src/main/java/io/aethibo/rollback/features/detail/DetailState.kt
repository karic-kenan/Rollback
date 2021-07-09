package io.aethibo.rollback.features.detail

import io.aethibo.rollback.domain.mapped.ProductItem
import io.aethibo.rollback.framework.mvibase.IState

data class DetailState(
    val isLoading: Boolean = false,
    val product: ProductItem? = null,
    val errorMessage: String? = null
) : IState