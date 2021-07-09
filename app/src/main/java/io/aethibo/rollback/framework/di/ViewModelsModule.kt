package io.aethibo.rollback.framework.di

import io.aethibo.rollback.features.detail.viewmodel.DetailViewModel
import io.aethibo.rollback.features.login.viewmodel.LoginViewModel
import io.aethibo.rollback.features.products.viewmodel.ProductsViewModel
import io.aethibo.rollback.features.profile.viewmodel.ProfileViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.module.Module
import org.koin.dsl.module

val viewModelsModule: Module = module {
    viewModel { LoginViewModel(get(), get()) }
    viewModel { ProductsViewModel(get()) }
    viewModel { DetailViewModel(get(), get()) }
    viewModel { ProfileViewModel() }
}