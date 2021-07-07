package io.aethibo.rollback.data.mapper

import io.aethibo.rollback.data.utils.EntityMapper
import io.aethibo.rollback.domain.mapped.UserItem
import io.aethibo.rollback.domain.response.AddressResponse
import io.aethibo.rollback.domain.response.NameResponse
import io.aethibo.rollback.domain.response.UserItemResponse

object UserMapper : EntityMapper<UserItemResponse, UserItem> {

    override fun mapFromEntity(entity: UserItemResponse): UserItem =
        UserItem(
            id = entity.id,
            firstName = entity.name.firstname,
            lastName = entity.name.lastname,
            city = entity.address.city,
            street = entity.address.street,
            zipCode = entity.address.zipcode,
            phone = entity.phone,
            username = entity.username,
            password = entity.password
        )

    override fun mapToEntity(domainModel: UserItem): UserItemResponse =
        UserItemResponse(
            id = domainModel.id,
            username = domainModel.username,
            password = domainModel.password,
            name = NameResponse(firstname = domainModel.firstName, lastname = domainModel.lastName),
            address = AddressResponse(
                city = domainModel.city,
                street = domainModel.street,
                zipcode = domainModel.zipCode
            ),
            phone = domainModel.phone
        )

    fun mapFromEntityList(entities: List<UserItemResponse>): List<UserItem> =
        entities.map { mapFromEntity(it) }
}