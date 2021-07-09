package io.aethibo.rollback.features.products.view

import android.os.Bundle
import android.view.View
import androidx.core.os.bundleOf
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
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

class ProductsFragment : Fragment(R.layout.fragment_products), View.OnClickListener,
    IView<ProductsState> {

    private val binding: FragmentProductsBinding by viewBinding()
    private val viewModel: ProductsViewModel by viewModel()
    private val productsAdapter: ProductsAdapter by lazy { ProductsAdapter() }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        handleIntents()
        setupAdapter()
        setupClickListeners()
        subscribeToObservers()
    }

    private fun setupClickListeners() {
        binding.btnAddProduct.setOnClickListener(this)
    }

    private fun setupAdapter() {
        binding.rvProductsList.adapter = productsAdapter

        productsAdapter.onProductEventClickListener { product ->
            val bundle = bundleOf("id" to product.id)
            findNavController().navigate(R.id.detailFragment, bundle)
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

    override fun onClick(view: View?) {
        when (view?.id) {
            R.id.btnAddProduct -> findNavController().navigate(ProductsFragmentDirections.toAddProduct())
        }
    }
}