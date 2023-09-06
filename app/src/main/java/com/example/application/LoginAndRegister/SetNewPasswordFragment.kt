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
    private val context: Context,
    private val createFragment: Interfaces.CreateFragment
) : Fragment() {
    private lateinit var binding: FragmentSetNewPasswordBinding
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentSetNewPasswordBinding.inflate(inflater, container, false)
        binding.toolbar.toolbarTitleTv.setText(R.string.toolbar_title_in_register_key)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        var validPassword = false
        binding.passwordEdit.doOnTextChanged { text, _, _, _ ->
            if (text?.isNotEmpty() == true)
                validPassword = text.toString().checkPattern(PASSWORD_PATTERN)
            binding.passwordInputLayout.checkSuccessCondition("Excellent", validPassword)
        }
        binding.confirmEdit.doOnTextChanged { text, _, _, _ ->
            if (binding.passwordEdit.text.toString().isNotEmpty() && validPassword) {
                binding.confirmInputLayout.checkSuccessCondition(
                    "Values match",
                    text.toString() == binding.passwordEdit.text.toString()
                )
            } else {
                binding.confirmInputLayout.error = "Values do not match"
            }
        }
        binding.toolbar.iconBack.setOnClickListener {
            createFragment.createFragment(ForgotPasswordFragment(createFragment, context))
        }
        binding.completeChanges.setOnClickListener {
            if (validPassword) {
                if (binding.passwordEdit.text.toString() == binding.confirmEdit.text.toString()) {
                    createFragment.createFragment(
                        SuccssedPasswordChangeFragment(
                            context,
                            createFragment
                        )
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
                createFragment.createFragment(ForgotPasswordFragment(createFragment, context))
            }

        }
        requireActivity().onBackPressedDispatcher.addCallback(callback)
    }
}