package io.aethibo.rollback.domain.request

data class AddProductRequest(
    val title: String,
    val description: String,
    val price: Double,
    val image: String = "https://i.pravatar.cc", // gives back random avatar
    val category: String
)
