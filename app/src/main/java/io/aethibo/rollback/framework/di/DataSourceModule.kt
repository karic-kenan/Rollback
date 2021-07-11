package io.aethibo.rollback.framework.di

import io.aethibo.data.datasource.MainRemoteDataSource
import io.aethibo.data.mapper.ProductMapper
import io.aethibo.data.mapper.UserMapper
import io.aethibo.rollback.framework.datasource.MainRemoteDataSourceImpl
import org.koin.core.module.Module
import org.koin.dsl.module

val dataSourceModule: Module = module {

    /**
     * Mappers
     */
    single { UserMapper }
    single { ProductMapper }

    /**
     * Data source
     */
    single<MainRemoteDataSource> { MainRemoteDataSourceImpl(get(), get(), get()) }
}