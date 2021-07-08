package io.aethibo.rollback.features.products.view

import android.os.Bundle
import android.view.View
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import by.kirich1409.viewbindingdelegate.viewBinding
import io.aethibo.rollback.R
import io.aethibo.rollback.databinding.FragmentProductsBinding
import io.aethibo.rollback.features.products.ProductsIntent.GetProducts
import io.aethibo.rollback.features.products.ProductsState
import io.aethibo.rollback.features.products.adapter.ProductsAdapter
import io.aethibo.rollback.features.products.viewmodel.ProductsViewModel
import io.aethibo.rollback.features.utils.snackBar
import io.aethibo.rollback.framework.mvibase.IView
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel
import timber.log.Timber

class ProductsFragment : Fragment(R.layout.fragment_products), IView<ProductsState> {

    private val binding: FragmentProductsBinding by viewBinding()
    private val viewModel: ProductsViewModel by viewModel()
    private val productsAdapter: ProductsAdapter by lazy { ProductsAdapter() }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        handleIntents()
        setupAdapter()
        subscribeToObservers()
    }

    private fun setupAdapter() {
        binding.rvProductsList.adapter = productsAdapter

        productsAdapter.onProductEventClickListener { product ->
            Timber.d("Product clicked: ${product.id}")
        }
    }

    private fun handleIntents() {
        lifecycleScope.launch {
            viewModel.intents.send(GetProducts)
        }
    }

    private fun subscribeToObservers() {
        lifecycleScope.launchWhenResumed {
            viewModel.state.collectLatest { render(it) }
        }
    }

    override fun render(state: ProductsState) {
        with(state) {
            binding.pbProducts.isVisible = isLoading

            productsAdapter.submitList(products)

            errorMessage?.let { snackBar(it) }
        }
    }
}