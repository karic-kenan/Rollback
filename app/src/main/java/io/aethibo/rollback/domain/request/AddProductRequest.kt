package io.aethibo.rollback.domain.request

data class AddProductRequest(
    val title: String,
    val price: Double,
    val description: String,
    val image: String,
    val category: String
)
