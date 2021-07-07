package io.aethibo.rollback.features.login.view

import android.os.Bundle
import android.view.View
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import by.kirich1409.viewbindingdelegate.viewBinding
import io.aethibo.rollback.R
import io.aethibo.rollback.databinding.FragmentLoginBinding
import io.aethibo.rollback.domain.request.UserRequest
import io.aethibo.rollback.features.login.LoginIntent
import io.aethibo.rollback.features.login.LoginIntent.GetUsers
import io.aethibo.rollback.features.login.LoginState
import io.aethibo.rollback.features.login.adapter.LoginAdapter
import io.aethibo.rollback.features.login.viewmodel.LoginViewModel
import io.aethibo.rollback.features.utils.snackBar
import io.aethibo.rollback.framework.mvibase.IView
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel
import timber.log.Timber

class LoginFragment : Fragment(R.layout.fragment_login), IView<LoginState> {

    private val binding: FragmentLoginBinding by viewBinding()
    private val viewModel: LoginViewModel by viewModel()
    private val userAdapter: LoginAdapter by lazy { LoginAdapter() }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupViews()
        handleIntents()
        setupAdapter()
        subscribeToObservers()
    }

    private fun setupViews() {
        binding.tvLoginTitle.text = getText(R.string.labelLoginTitle)
        binding.tvLoginSubtitle.text = getText(R.string.labelLoginSubtitle)
    }

    private fun setupAdapter() {
        binding.rvUsersList.adapter = userAdapter

        userAdapter.onUserEventClickListener { user ->
            Timber.d("User clicked: ${user.username} ${user.password}")
            lifecycleScope.launch {
                val userRequest = UserRequest(user.username, user.password)
                viewModel.intents.send(LoginIntent.LoginUser(userRequest))
            }
        }
    }

    private fun handleIntents() {
        lifecycleScope.launch {
            viewModel.intents.send(GetUsers)
        }
    }

    private fun subscribeToObservers() {
        lifecycleScope.launchWhenResumed {
            viewModel.state.collect { render(it) }
        }
    }

    override fun render(state: LoginState) {
        with(state) {
            binding.pbLogin.isVisible = isLoading

            userAdapter.submitList(users)

            token?.let { findNavController().navigate(LoginFragmentDirections.loginToProducts()) }

            errorMessage?.let { snackBar(it) }
        }
    }
}