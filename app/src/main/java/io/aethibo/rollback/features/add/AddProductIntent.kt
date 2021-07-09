package io.aethibo.rollback.features.add

import io.aethibo.rollback.domain.request.AddProductRequest
import io.aethibo.rollback.framework.mvibase.IIntent

sealed class AddProductIntent : IIntent {
    data class AddProduct(val product: AddProductRequest) : AddProductIntent()
}
