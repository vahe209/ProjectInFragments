package com.example.application

import android.content.Context
import android.content.Intent
import android.graphics.drawable.GradientDrawable
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.navigation.fragment.findNavController
import com.example.application.databinding.FragmentLoginBinding

class LoginFragment(private val context: Context) : Fragment() {
    private lateinit var binding: FragmentLoginBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentLoginBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.textForRegistration.setOnClickListener {
           createRegisterFragment()
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
    private fun createRegisterFragment() {
        activity?.supportFragmentManager?.beginTransaction()
            ?.replace(R.id.main_fragment_layout, RegisterFragment(context))?.commit()
    }
}
