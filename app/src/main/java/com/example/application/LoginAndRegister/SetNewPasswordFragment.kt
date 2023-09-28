package com.example.application.LoginAndRegister

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.Fragment
import com.example.application.databinding.FragmentSetNewPasswordBinding
import com.example.application.LoginAndRegister.interfaces.Interfaces
import com.example.application.R
import com.example.application.util.PASSWORD_PATTERN
import com.example.application.util.checkPattern

class SetNewPasswordFragment(
    private val createFragment: Interfaces.CreateFragment) : Fragment() {
    private lateinit var binding: FragmentSetNewPasswordBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        binding = FragmentSetNewPasswordBinding.inflate(inflater, container, false)
        binding.toolbar.toolbarTitleTv.setText(R.string.toolbar_title_in_register_key)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        var validPassword = false
        binding.passwordEdt.doOnTextChanged { text, _, _, _ ->
            if (text?.isNotEmpty() == true) validPassword =
                text.toString().checkPattern(PASSWORD_PATTERN)
            binding.passwordInputLayout.checkSuccessCondition("Excellent", validPassword)
        }
        binding.confirmEdt.doOnTextChanged { text, _, _, _ ->
            if (binding.passwordEdt.text.toString().isNotEmpty() && validPassword) {
                binding.confirmInputLayout.checkSuccessCondition(
                    "Values match", text.toString() == binding.passwordEdt.text.toString()
                )
            } else {
                binding.confirmInputLayout.error = "Values do not match"
            }
        }
        binding.toolbar.iconBackImg.setOnClickListener {
            createFragment.createFragment(ForgotPasswordFragment(createFragment))
        }
        binding.completeChangesBtn.setOnClickListener {
            if (validPassword) {
                if (binding.passwordEdt.text.toString() == binding.confirmEdt.text.toString()) {
                    createFragment.createFragment(
                        SuccssedPasswordChangeFragment(createFragment)
                    )
                } else {
                    binding.confirmInputLayout.error = "Values do not match"
                }
            } else {
                binding.passwordInputLayout.error = "Incorrect password type"
            }
        }
        val callback = object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                createFragment.createFragment(ForgotPasswordFragment(createFragment))
            }
        }
        requireActivity().onBackPressedDispatcher.addCallback(callback)
    }
}