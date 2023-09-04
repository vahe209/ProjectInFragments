package com.example.application

import android.content.Context
import android.graphics.drawable.GradientDrawable
import android.os.Bundle
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.example.application.customClasses.serErrorTextAlignment
import com.example.application.databinding.FragmentLoginBinding
import com.example.application.interfaces.Interfaces
import java.util.Objects

class LoginFragment(
    private val context: Context,
    private val createFragment: Interfaces.CreateFragment) : Fragment() {
    private lateinit var binding: FragmentLoginBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = FragmentLoginBinding.inflate(inflater, container, false)
        binding.toolbar.toolbarTitleTv.text = resources.getText(R.string.toolbar_title_in_login_page_key)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.textForRegistration.setOnClickListener {
            createFragment.createFragment(RegisterFragment(context, createFragment))
        }
        binding.btnLogin.setOnClickListener {
            val isLoginValid = checkEmail()
            val isPasswordValid = checkPassword()
            if (isLoginValid && isPasswordValid) {
                // TODO:  login call
            }
        }
        binding.forgotPassText.setOnClickListener {
            createFragment.createFragment(ForgotPasswordFragment(createFragment,context))
        }
    }

    private fun checkPassword(): Boolean {
        return if (binding.passwordEdit.text.toString().isEmpty()) {
            binding.passwordInputLayout.error = "Password is required"
            false
        } else {
            binding.passwordInputLayout.error = null
            true
        }
    }

    private fun checkEmail(): Boolean {
        return if (binding.emailEdit.text.toString().isEmpty()) {
            binding.emailInputLayout.error = "Email is required"
            false
        } else {
            binding.emailInputLayout.error = null
            true
        }
    }
}
