package io.aethibo.domain.mapped

data class UserItem(
    val id: Int,
    val firstName: String,
    val lastName: String,
    val city: String,
    val street: String,
    val zipCode: String,
    val phone: String,
    val username: String,
    val password: String
)
