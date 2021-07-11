package io.aethibo.rollback.framework.di

import io.aethibo.data.repositories.DefaultMainRepository
import io.aethibo.data.repositories.MainRepository
import org.koin.core.module.Module
import org.koin.dsl.module

val repositoriesModule: Module = module {
    single<MainRepository> { DefaultMainRepository(get()) }
}