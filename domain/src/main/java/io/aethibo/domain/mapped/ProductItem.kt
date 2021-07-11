package io.aethibo.domain.mapped

data class ProductItem(
    val id: Int,
    val title: String,
    val description: String,
    val image: String,
    val category: String,
    val price: Double,
)
