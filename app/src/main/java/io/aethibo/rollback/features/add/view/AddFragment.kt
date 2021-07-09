package io.aethibo.rollback.features.add.view

import android.os.Bundle
import android.view.View
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import by.kirich1409.viewbindingdelegate.viewBinding
import com.afollestad.vvalidator.field.input.InputLayoutField
import com.afollestad.vvalidator.form
import io.aethibo.rollback.R
import io.aethibo.rollback.databinding.FragmentAddBinding
import io.aethibo.rollback.domain.request.AddProductRequest
import io.aethibo.rollback.features.add.AddProductIntent.AddProduct
import io.aethibo.rollback.features.add.AddProductState
import io.aethibo.rollback.features.add.viewmodel.AddViewModel
import io.aethibo.rollback.features.utils.snackBar
import io.aethibo.rollback.framework.mvibase.IView
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel

class AddFragment : Fragment(R.layout.fragment_add), IView<AddProductState> {

    private val binding: FragmentAddBinding by viewBinding()
    private val viewModel: AddViewModel by viewModel()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        validateForm()
        subscribeToObservers()
    }

    private fun validateForm() {
        form {
            inputLayout(
                binding.ilProductTitle,
                name = "Title", builder = InputLayoutField::isNotEmpty
            )
            inputLayout(
                binding.ilProductDescription,
                name = "Description", builder = InputLayoutField::isNotEmpty
            )
            inputLayout(
                binding.ilProductCategory,
                name = "Category", builder = InputLayoutField::isNotEmpty
            )
            inputLayout(binding.ilProductPrice, name = "Price") { isNumber().greaterThan(0) }
            submitWith(binding.btnAddProduct) { submitProduct() }
        }
    }

    private fun submitProduct() {
        val title = binding.ilProductTitle.editText?.text.toString()
        val description = binding.ilProductDescription.editText?.text.toString()
        val category = binding.ilProductCategory.editText?.text.toString()
        val price = binding.ilProductPrice.editText?.text.toString().toDouble()

        val product = AddProductRequest(
            title = title,
            description = description,
            price = price,
            category = category
        )

        lifecycleScope.launch {
            viewModel.intents.send(AddProduct(product))
        }
    }

    private fun subscribeToObservers() {
        lifecycleScope.launchWhenResumed {
            viewModel.state.collectLatest { render(it) }
        }
    }

    override fun render(state: AddProductState) {
        with(state) {
            binding.pbAddProduct.isVisible = isLoading

            if (product != null) {
                snackBar("Product added successfully")
                findNavController().popBackStack()
            }

            errorMessage?.let { snackBar(it) }
        }
    }
}