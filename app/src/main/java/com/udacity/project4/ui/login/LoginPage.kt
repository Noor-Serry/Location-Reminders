package com.udacity.project4.ui.login

import android.annotation.SuppressLint
import android.app.Activity.RESULT_OK
import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.ActivityResultLauncher
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.firebase.ui.auth.AuthUI
import com.firebase.ui.auth.ErrorCodes
import com.firebase.ui.auth.FirebaseAuthUIActivityResultContract
import com.firebase.ui.auth.data.model.FirebaseAuthUIAuthenticationResult
import com.google.android.material.snackbar.Snackbar
import com.udacity.project4.R
import com.udacity.project4.databinding.PageLoginBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LoginPage : Fragment() {

    private lateinit var binding: PageLoginBinding
    private val viewModel: LogInViewModel by viewModels()


    private lateinit var firebaseUILauncher: ActivityResultLauncher<Intent>

    override fun onAttach(context: Context) {
        super.onAttach(context)
        firebaseUILauncher = registerForActivityResult(
            FirebaseAuthUIActivityResultContract(),
            this::onActivityResult
        )
        startLoginUi()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = PageLoginBinding.inflate(inflater)
        return binding.root
    }

    @SuppressLint("RestrictedApi", "SuspiciousIndentation")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

    }

    private fun startLoginUi() {
        val providers = arrayListOf(
            AuthUI.IdpConfig.GoogleBuilder().build(),
            AuthUI.IdpConfig.EmailBuilder().build(),
            AuthUI.IdpConfig.PhoneBuilder().build()
        )
        val authUIIntent = AuthUI.getInstance().createSignInIntentBuilder()
            .setAvailableProviders(providers)
            .setIsSmartLockEnabled(false)
            .build()
        firebaseUILauncher.launch(authUIIntent)
    }

    private fun onActivityResult(result: FirebaseAuthUIAuthenticationResult) {
        if (result.resultCode == RESULT_OK)
            onSignInSuccess()
        else onSignInFailure(result.idpResponse?.error?.errorCode)
    }

    private fun onSignInFailure(errorCode: Int?) {
        when (errorCode) {
            null -> requireActivity().finish()
            ErrorCodes.NO_NETWORK -> showError(getString(R.string.network_error))
            else -> showError(getString(R.string.error))
        }

    }


    private fun showError(errorMessage: String) {
        Snackbar.make(
            binding.root,
            errorMessage,
            Snackbar.LENGTH_LONG
        ).show()
    }


    private fun onSignInSuccess() {
        viewModel.setUserLoginState(true)
        goToReminderListFragment()
    }

    private fun goToReminderListFragment() {
        findNavController().navigate(R.id.action_loginPage_to_remindersFragment)
    }


}