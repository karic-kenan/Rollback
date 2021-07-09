package io.aethibo.rollback.features.detail.view

import android.os.Bundle
import android.view.View
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import by.kirich1409.viewbindingdelegate.viewBinding
import coil.load
import coil.transform.RoundedCornersTransformation
import io.aethibo.rollback.R
import io.aethibo.rollback.databinding.FragmentDetailBinding
import io.aethibo.rollback.domain.mapped.ProductItem
import io.aethibo.rollback.features.detail.DetailIntent
import io.aethibo.rollback.features.detail.DetailState
import io.aethibo.rollback.features.detail.viewmodel.DetailViewModel
import io.aethibo.rollback.features.utils.snackBar
import io.aethibo.rollback.framework.mvibase.IView
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel

class DetailFragment : Fragment(R.layout.fragment_detail), View.OnClickListener,
    IView<DetailState> {

    private val binding: FragmentDetailBinding by viewBinding()
    private val viewModel: DetailViewModel by viewModel()
    private var productId: Int? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        productId = arguments?.getInt("id")

        handleIntents()
        setupClickListeners()
        subscribeToObservers()
    }

    private fun setupClickListeners() {
        binding.btnDeleteProduct.setOnClickListener(this)
    }

    private fun handleIntents() {
        lifecycleScope.launch {
            productId?.let { viewModel.intents.send(DetailIntent.GetProduct(it)) }
        }
    }

    private fun subscribeToObservers() {
        lifecycleScope.launchWhenResumed {
            viewModel.state.collect { render(it) }
        }
    }

    override fun render(state: DetailState) {
        with(state) {
            binding.pbDetail.isVisible = isLoading

            product?.let { handleProduct(it) }

            isProductDeleted?.let { if (it) snackBar("Product is deleted") }

            errorMessage?.let { snackBar(it) }
        }
    }

    private fun handleProduct(product: ProductItem) {
        binding.itemProductImage.load(product.image) {
            crossfade(true)
            transformations(RoundedCornersTransformation(bottomLeft = 30f, bottomRight = 30f))
        }
        binding.itemProductTitle.text = product.title
        binding.itemProductDescription.text = product.description
        binding.itemProductCategory.text = product.category
        binding.itemProductPrice.text = "$${product.price}"
    }

    override fun onClick(view: View?) {
        when (view?.id) {
            R.id.btnDeleteProduct -> lifecycleScope.launch {
                productId?.let { viewModel.intents.send(DetailIntent.DeleteProduct(it)) }
            }
        }
    }
}