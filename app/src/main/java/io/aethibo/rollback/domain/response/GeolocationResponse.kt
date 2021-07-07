package io.aethibo.rollback.domain.response

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class GeolocationResponse(
    val lat: String = "",
    val long: String = ""
)