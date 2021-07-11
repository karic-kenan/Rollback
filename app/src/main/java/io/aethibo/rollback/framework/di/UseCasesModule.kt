package io.aethibo.rollback.framework.di

import io.aethibo.usecases.*
import org.koin.core.module.Module
import org.koin.dsl.module

val useCasesModule: Module = module {
    single<LoginUserUseCase> { LoginUserUseCaseImpl(get()) }
    single<GetUsersUseCase> { GetUsersUseCaseImpl(get()) }
    single<GetCategoriesUseCase> { GetCategoriesUseCaseImpl(get()) }
    single<GetProductUseCase> { GetProductUseCaseImpl(get()) }
    single<GetProductsUseCase> { GetProductsUseCaseImpl(get()) }
    single<AddProductUseCase> { AddProductUseCaseImpl(get()) }
    single<DeleteProductUseCase> { DeleteProductUseCaseImpl(get()) }
}