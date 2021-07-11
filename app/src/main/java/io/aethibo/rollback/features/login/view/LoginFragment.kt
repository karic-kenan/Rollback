package io.aethibo.rollback.features.login.view

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
import io.aethibo.rollback.databinding.FragmentLoginBinding
import io.aethibo.rollback.domain.mapped.UserItem
import io.aethibo.rollback.domain.request.UserRequest
import io.aethibo.rollback.features.login.LoginIntent
import io.aethibo.rollback.features.login.LoginIntent.GetUsers
import io.aethibo.rollback.features.login.LoginState
import io.aethibo.rollback.features.login.viewmodel.LoginViewModel
import io.aethibo.rollback.features.utils.snackBar
import io.aethibo.rollback.framework.mvibase.IView
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel

class LoginFragment : Fragment(R.layout.fragment_login), IView<LoginState> {

    private val binding: FragmentLoginBinding by viewBinding()
    private val viewModel: LoginViewModel by viewModel()
    private var isHandled = false

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupViews()
        handleIntents()
        validateForm()
        subscribeToObservers()
    }

    private fun setupViews() {
        binding.tvLoginTitle.text = getText(R.string.labelLoginTitle)
        binding.tvLoginSubtitle.text = getText(R.string.labelLoginSubtitle)
    }

    private fun handleIntents() {
        lifecycleScope.launch { viewModel.intents.send(GetUsers) }
    }

    private fun subscribeToObservers() {
        lifecycleScope.launchWhenResumed {
            viewModel.state.collect { render(it) }
        }
    }

    private fun validateForm() {
        form {
            inputLayout(binding.tilLoginUsername, builder = InputLayoutField::isNotEmpty)
            inputLayout(binding.tilLoginPassword, builder = InputLayoutField::isNotEmpty)
            submitWith(binding.btnSignInUser) {
                val username: String = it["tilLoginUsername"]?.value.toString()
                val password: String = it["tilLoginPassword"]?.value.toString()

                lifecycleScope.launch {
                    val userRequest = UserRequest(username, password)
                    viewModel.intents.send(LoginIntent.LoginUser(userRequest))
                }
            }
        }
    }

    override fun render(state: LoginState) {
        with(state) {
            binding.pbLogin.isVisible = isLoading

            getUserFromUsers(users)

            token?.let { findNavController().navigate(LoginFragmentDirections.loginToProducts()) }

            errorMessage?.let { snackBar(it) }
        }
    }

    private fun getUserFromUsers(users: List<UserItem>) {
        if (users.isNotEmpty() && !isHandled)
            users.random().apply {
                binding.tilLoginUsername.editText?.setText(username)
                binding.tilLoginPassword.editText?.setText(password)
                binding.btnSignInUser.isEnabled = true
                isHandled = true
            }
    }
}