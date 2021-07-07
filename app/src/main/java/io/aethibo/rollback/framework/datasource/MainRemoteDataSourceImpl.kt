package io.aethibo.rollback.framework.datasource

import io.aethibo.rollback.data.datasource.MainRemoteDataSource
import io.aethibo.rollback.data.remote.api.ApiService

class MainRemoteDataSourceImpl(private val apiService: ApiService) : MainRemoteDataSource {
}