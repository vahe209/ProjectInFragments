package com.example.application

import android.content.Context
import android.graphics.drawable.GradientDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.example.application.databinding.FragmentLoginBinding
import com.example.application.interfaces.Interfaces

class LoginFragment(
    private val context: Context,
    private val createFragment: Interfaces.CreateFragment) : Fragment() {
    private lateinit var binding: FragmentLoginBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = FragmentLoginBinding.inflate(inflater, container, false)
        binding.toolbar.toolbarTitle.text = resources.getText(R.string.toolbar_title_in_login_page)
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
            } else {
                println("Error")
            }
        }
        binding.forgotPassText.setOnClickListener {
            createFragment.createFragment(ForgotPasswordFragment(createFragment,context))
        }
    }

    private fun checkPassword(): Boolean {
        val drawable: GradientDrawable = binding.passwordEdit.background as GradientDrawable
        return if (binding.passwordEdit.text.toString().isEmpty()) {
            binding.passwordInputLayout.error = "Password is required"
            drawable.setStroke(1, ContextCompat.getColor(context, R.color.bg_btn))
            false
        } else {
            drawable.setStroke(1, ContextCompat.getColor(context, R.color.green))
            binding.passwordInputLayout.error = null
            true
        }
    }

    private fun checkEmail(): Boolean {
        val drawable: GradientDrawable = binding.emailEdit.background as GradientDrawable
        return if (binding.emailEdit.text.toString().isEmpty()) {
            drawable.setStroke(1, ContextCompat.getColor(context, R.color.bg_btn))
            binding.emailInputLayout.error = "Email is required"
            false
        } else {
            drawable.setStroke(1, ContextCompat.getColor(context, R.color.green))
            binding.emailInputLayout.error = null
            true
        }
    }

    override fun onStop() {
        super.onStop()
        var drawable: GradientDrawable = binding.emailEdit.background as GradientDrawable
        drawable.setStroke(1, ContextCompat.getColor(context, R.color.bg_color))
         drawable = binding.passwordEdit.background as GradientDrawable
        drawable.setStroke(1, ContextCompat.getColor(context, R.color.bg_color))
    }
}
