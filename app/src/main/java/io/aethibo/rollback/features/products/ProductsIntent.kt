package io.aethibo.rollback.features.products

import io.aethibo.rollback.framework.mvibase.IIntent

sealed class ProductsIntent : IIntent {
    object GetProducts : ProductsIntent()
}