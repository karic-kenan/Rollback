package io.aethibo.rollback.framework.di

import io.aethibo.rollback.usecases.GetUsersUseCase
import io.aethibo.rollback.usecases.GetUsersUseCaseImpl
import io.aethibo.rollback.usecases.LoginUserUseCase
import io.aethibo.rollback.usecases.LoginUserUseCaseImpl
import org.koin.core.module.Module
import org.koin.dsl.module

val useCasesModule: Module = module {
    single<LoginUserUseCase> { LoginUserUseCaseImpl(get()) }
    single<GetUsersUseCase> { GetUsersUseCaseImpl(get()) }
}