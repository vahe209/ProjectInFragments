package com.example.application.LoginAndRegister

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.core.view.isVisible
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.Fragment
import com.example.application.databinding.FragmentForgotPasswordBinding
import com.example.application.LoginAndRegister.interfaces.Interfaces
import com.example.application.R

class ForgotPasswordFragment(
    private val createFragment: Interfaces.CreateFragment,
    private val context: Context)
    : Fragment(),
    WrongDataFragment.FragmentInteractionListener {
    private lateinit var binding: FragmentForgotPasswordBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentForgotPasswordBinding.inflate(inflater, container, false)
        binding.toolbar.toolbarTitleTv.text =
            resources.getText(R.string.toolbar_title_in_forgot_pass_key)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.toolbar.iconBack.setOnClickListener {
            createFragment.createFragment(LoginFragment(context, createFragment))
        }
        binding.box1.doOnTextChanged { text, _, _, _ ->
            if (text != null) {
                if (text.isNotEmpty()) {
                    binding.box2.requestFocus()
                    checkFields()
                }
            }
        }
        binding.box2.doOnTextChanged { text, _, _, _ ->
            if (text != null) {
                if (text.isNotEmpty()) {
                    binding.box3.requestFocus()
                    checkFields()
                }
            }
        }
        binding.box3.doOnTextChanged { text, _, _, _ ->
            if (text != null) {
                if (text.isNotEmpty()) {
                    binding.box4.requestFocus()
                    checkFields()
                }
            }
        }
        binding.box4.doOnTextChanged { text, _, _, _ ->
            if (text != null) {
                if (text.isNotEmpty()) {
                    binding.box5.requestFocus()
                    checkFields()
                }
            }
        }
        binding.box5.doOnTextChanged { text, _, _, _ ->
            if (text != null) {
                if (text.isNotEmpty()) {
                    binding.box6.requestFocus()
                    checkFields()
                }
            }
        }
        binding.box6.doOnTextChanged { text, _, _, _ ->
            if (text != null) {
                if (text.isNotEmpty()) {
                    binding.box6.clearFocus()
                    checkFields()
                }
            }
        }
        binding.sendCodeBtn.setOnClickListener {
            if (userInfoEdit()) {
                binding.sendCodeBtn.isVisible = false
                binding.sendCodeLayout.isVisible = true
                binding.forgotPassEdit.isFocusable = false
            }
        }
        val callback = object: OnBackPressedCallback(true){
            override fun handleOnBackPressed() {
                createFragment.createFragment(LoginFragment(context,createFragment))
            }

        }
        requireActivity().onBackPressedDispatcher.addCallback(callback)
    }

    private fun userInfoEdit(): Boolean {
        return if (binding.forgotPassEdit.text.toString().isNotEmpty()) {
            true
        } else {
            openErrorFragment()
            false
        }
    }

    private fun openErrorFragment() {
        binding.toolbar.iconBack.isVisible = false
        binding.toolbar.toolbarTitleTv.isVisible = false
        val wrongDataFragment =
            WrongDataFragment(resources.getString(R.string.enter_email_or_phone_error_key))
        wrongDataFragment.setFragmentInteractionListener(this)
        activity?.supportFragmentManager?.beginTransaction()
            ?.replace(R.id.toolbar, wrongDataFragment)?.commit()
    }

    override fun onCloseButtonPressed() {
        binding.toolbar.iconBack.isVisible = true
        binding.toolbar.toolbarTitleTv.isVisible = true
        val fragment = activity?.supportFragmentManager?.findFragmentById(R.id.toolbar)
        if (fragment is WrongDataFragment) {
            activity?.supportFragmentManager?.beginTransaction()?.remove(fragment)?.commit()
        }
    }

    private fun checkFields() {
        val box1 = binding.box1.text.toString().isNotEmpty()
        val box2 = binding.box2.text.toString().isNotEmpty()
        val box3 = binding.box3.text.toString().isNotEmpty()
        val box4 = binding.box4.text.toString().isNotEmpty()
        val box5 = binding.box5.text.toString().isNotEmpty()
        val box6 = binding.box6.text.toString().isNotEmpty()
        if (box1 && box2 && box3 && box4 && box5 && box6) {
            createFragment.createFragment(SetNewPasswordFragment(context, createFragment))
        }
    }
}