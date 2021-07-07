package io.aethibo.rollback.domain.response

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class NameResponse(
    val firstname: String = "",
    val lastname: String = ""
)