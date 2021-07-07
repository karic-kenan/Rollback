package io.aethibo.rollback.framework.di

import io.aethibo.rollback.data.datasource.MainRemoteDataSource
import io.aethibo.rollback.framework.datasource.MainRemoteDataSourceImpl
import org.koin.core.module.Module
import org.koin.dsl.module

val dataSourceModule: Module = module {
    single<MainRemoteDataSource> { MainRemoteDataSourceImpl(get()) }
}