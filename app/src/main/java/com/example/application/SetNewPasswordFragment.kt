package com.example.application

import android.content.Context
import android.content.res.ColorStateList
import android.graphics.drawable.GradientDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.core.content.ContextCompat
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.Fragment
import com.example.application.databinding.FragmentSetNewPasswordBinding
import com.example.application.interfaces.Interfaces
import java.util.regex.Matcher
import java.util.regex.Pattern

class SetNewPasswordFragment(
    private val context: Context,
    private val createFragment: Interfaces.CreateFragment) : Fragment() {
    private lateinit var binding : FragmentSetNewPasswordBinding
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = FragmentSetNewPasswordBinding.inflate(inflater, container, false)
        binding.toolbar.toolbarTitle.setText(R.string.toolbar_title_in_rester_password_page)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        var validPassword = false
        binding.passwordEdit.doOnTextChanged { text, _, _, _ ->
            val drawable : GradientDrawable = binding.passwordEdit.background as GradientDrawable
            if (isValidPass(text.toString())) {
                binding.passwordInputLayout.setErrorTextColor(
                    ColorStateList.valueOf(ContextCompat.getColor(context, R.color.green)))
                binding.passwordInputLayout.error = "Excellent"
                drawable.setStroke(1, ContextCompat.getColor(context, R.color.green))
                validPassword = true
            } else {
                binding.passwordInputLayout.error = null
                validPassword = false
            }
        }
        binding.confirmEdit.doOnTextChanged { text, _, _, _ ->
            val drawable : GradientDrawable = binding.confirmEdit.background as GradientDrawable
            if (text.toString() == binding.passwordEdit.text.toString() && binding.passwordEdit.text.toString().isNotEmpty())
            {
                binding.confirmInputLayout.apply {
                    binding.confirmInputLayout.setErrorTextColor(
                        ColorStateList.valueOf(ContextCompat.getColor(context, R.color.green)))
                    drawable.setStroke(1, ContextCompat.getColor(context, R.color.green))
                    binding.confirmInputLayout.error = "Values match"
                }
            } else {
                binding.confirmInputLayout.apply {
                    binding.confirmInputLayout.setErrorTextColor(
                        ColorStateList.valueOf(ContextCompat.getColor(context, R.color.bg_btn)))
                    drawable.setStroke(1, ContextCompat.getColor(context, R.color.bg_btn))
                    binding.confirmInputLayout.error = "Values do not match"

                }
            }
        }
        binding.toolbar.backArrow.setOnClickListener {
            createFragment.createFragment(ForgotPasswordFragment(createFragment,context))
        }
        binding.compelteChanges.setOnClickListener {
            if (validPassword){
                if (binding.passwordEdit.text.toString() == binding.confirmEdit.text.toString()){
                    createFragment.createFragment(SuccssedPasswordChangeFragment(context,createFragment))
                }else{
                    val drawable : GradientDrawable = binding.confirmEdit.background as GradientDrawable
                    drawable.setStroke(1, ContextCompat.getColor(context, R.color.bg_btn))
                    binding.confirmInputLayout.setErrorTextColor(
                        ColorStateList.valueOf(ContextCompat.getColor(context, R.color.bg_btn)))
                    binding.confirmInputLayout.error = "Values do not match"
                }
            }else{
                val drawable : GradientDrawable = binding.passwordEdit.background as GradientDrawable
                drawable.setStroke(1, ContextCompat.getColor(context, R.color.bg_btn))
                binding.passwordInputLayout.setErrorTextColor(
                    ColorStateList.valueOf(ContextCompat.getColor(context, R.color.bg_btn)))
                binding.passwordInputLayout.error ="Incorrect password type"
            }
        }
        val callback = object: OnBackPressedCallback(true){
            override fun handleOnBackPressed() {
                createFragment.createFragment(ForgotPasswordFragment(createFragment,context))
            }

        }
        requireActivity().onBackPressedDispatcher.addCallback(callback)
    }

    @Suppress("UNREACHABLE_CODE")
    private fun isValidPass(password: String): Boolean {
        val regex = "^(?=.*\\d)" +
                "(?=.*[a-z])(?=.*[A-Z])" +
                "(?=.*[!@#$%^&*()_+~`<>?:{}])" +
                "(?=\\S+$).{8,20}$"
        val p: Pattern = Pattern.compile(regex)
        val m: Matcher = p.matcher(password)
        return m.matches()
    }
    override fun onStop() {
        super.onStop()
        var drawable = binding.passwordEdit.background as GradientDrawable
        drawable.setStroke(1,ContextCompat.getColor(context, R.color.bg_color))
    }

}