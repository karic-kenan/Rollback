package io.aethibo.rollback.domain.response

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class AddressResponse(
    val city: String = "",
    val geolocation: GeolocationResponse = GeolocationResponse(),
    val number: Int = 0,
    val street: String = "",
    val zipcode: String = ""
)