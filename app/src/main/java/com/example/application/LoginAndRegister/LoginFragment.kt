package com.example.application.LoginAndRegister

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.application.databinding.FragmentLoginBinding
import com.example.application.LoginAndRegister.interfaces.Interfaces
import com.example.application.R

class LoginFragment(
 private val createFragment: Interfaces.CreateFragment
) : Fragment() {
    private lateinit var binding: FragmentLoginBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        binding = FragmentLoginBinding.inflate(inflater, container, false)
        binding.toolbar.toolbarTitleTv.text =
            resources.getText(R.string.toolbar_title_in_login_page_key)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.forRegisterTv.setOnClickListener {
            createFragment.createFragment(RegisterFragment(createFragment))
        }
        binding.loginBtn.setOnClickListener {
            val isLoginValid = checkEmail()
            val isPasswordValid = checkPassword()
            if (isLoginValid && isPasswordValid) {
                // TODO:  login call
            }
        }
        binding.forgotPassTv.setOnClickListener {
            createFragment.createFragment(ForgotPasswordFragment(createFragment))
        }
    }

    private fun checkPassword(): Boolean {
        return if (binding.passwordEdt.text.toString().isEmpty()) {
            binding.passwordInputLayout.error = "Password is required"
            false
        } else {
            binding.passwordInputLayout.error = null
            true
        }
    }

    private fun checkEmail(): Boolean {
        return if (binding.emailEdt.text.toString().isEmpty()) {
            binding.emailInputLayout.error = "Email is required"
            false
        } else {
            binding.emailInputLayout.error = null
            true
        }
    }
}
