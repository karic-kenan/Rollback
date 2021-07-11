package io.aethibo.domain.response

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class UserItemResponse(
    val address: AddressResponse = AddressResponse(),
    val email: String = "",
    val id: Int = 0,
    val name: NameResponse = NameResponse(),
    val password: String = "",
    val phone: String = "",
    val username: String = "",
    @Json(name = "__v")
    val v: Int = 0
)