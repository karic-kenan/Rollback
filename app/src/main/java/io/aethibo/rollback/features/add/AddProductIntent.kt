package io.aethibo.rollback.features.add

import io.aethibo.domain.request.AddProductRequest
import io.aethibo.rollback.framework.mvibase.IIntent

sealed class AddProductIntent : IIntent {
    object GetCategories : AddProductIntent()
    data class AddProduct(val product: AddProductRequest) : AddProductIntent()
}
