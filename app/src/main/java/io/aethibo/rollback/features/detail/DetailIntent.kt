package io.aethibo.rollback.features.detail

import io.aethibo.rollback.framework.mvibase.IIntent

sealed class DetailIntent : IIntent {
    data class GetProduct(val id: Int) : DetailIntent()
    data class DeleteProduct(val id: Int): DetailIntent()
}