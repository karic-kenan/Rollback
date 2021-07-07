package io.aethibo.rollback.domain.response

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class ProductItemResponse(
    val category: String = "",
    val description: String = "",
    val id: Int = 0,
    val image: String = "",
    val price: Double = 0.0,
    val title: String = ""
)