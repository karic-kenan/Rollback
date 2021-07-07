package io.aethibo.rollback.data.repositories

import io.aethibo.rollback.data.datasource.MainRemoteDataSource

class DefaultMainRepository(private val remoteAccess: MainRemoteDataSource) : MainRepository {
}