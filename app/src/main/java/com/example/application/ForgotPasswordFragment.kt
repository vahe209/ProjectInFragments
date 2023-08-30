package com.example.application

import android.content.Context
import android.graphics.drawable.GradientDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.Fragment
import com.example.application.databinding.FragmentForgotPasswordBinding

class ForgotPasswordFragment(
    private val createFragment: CreateFragment,
    private val context: Context)
    : Fragment(),
    WrongDataFragment.FragmentInteractionListener{
    private lateinit var binding: FragmentForgotPasswordBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = FragmentForgotPasswordBinding.inflate(inflater, container, false)
        binding.toolbar.toolbarTitle.text = resources.getText(R.string.toolbar_title_in_forgot_pass_page)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.toolbar.backArrow.setOnClickListener {
            createFragment.createFragment(LoginFragment(context, createFragment))
        }
        binding.box1.doOnTextChanged { text, _, _, _ ->
            if (text != null) {
                if (text.isNotEmpty()) {
                    binding.box2.requestFocus()
                }
            }
        }
        binding.box2.doOnTextChanged { text, _, _, _ ->
            if (text != null) {
                if (text.isNotEmpty()) {
                    binding.box3.requestFocus()
                }
            }
        }
        binding.box3.doOnTextChanged { text, _, _, _ ->
            if (text != null) {
                if (text.isNotEmpty()) {
                    binding.box4.requestFocus()
                }
            }
        }
        binding.box4.doOnTextChanged { text, _, _, _ ->
            if (text != null) {
                if (text.isNotEmpty()) {
                    binding.box5.requestFocus()
                }
            }
        }
        binding.box5.doOnTextChanged { text, _, _, _ ->
            if (text != null) {
                if (text.isNotEmpty()) {
                    binding.box6.requestFocus()
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
            }
        }
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
        binding.toolbar.backArrow.isVisible = false
        binding.toolbar.toolbarTitle.isVisible = false
        val wrongDataFragment = WrongDataFragment(resources.getString(R.string.enter_email_or_phone_error_text))
        wrongDataFragment.setFragmentInteractionListener(this)
        activity?.supportFragmentManager?.beginTransaction()?.replace(R.id.toolbar, wrongDataFragment)?.commit()
    }

    override fun onCloseButtonPressed() {
        binding.toolbar.backArrow.isVisible = true
        binding.toolbar.toolbarTitle.isVisible = true
        val fragment = activity?.supportFragmentManager?.findFragmentById(R.id.toolbar)
        if (fragment is WrongDataFragment) {
            activity?.supportFragmentManager?.beginTransaction()?.remove(fragment)?.commit()
        }
    }

    private fun checkFields() {
        val box1 = checkBox1(binding.box1.text.toString())
        val box2 = checkBox2(binding.box2.text.toString())
        val box3 = checkBox3(binding.box3.text.toString())
        val box4 = checkBox4(binding.box4.text.toString())
        val box5 = checkBox5(binding.box5.text.toString())
        val box6 = checkBox6(binding.box6.text.toString())
        if (box1 && box2 && box3 && box4 && box5 && box6) {
            createFragment.createFragment(SetNewPasswordFragment())
        }
    }

    private fun checkBox1(text: String): Boolean {
        val drawable: GradientDrawable = binding.box1.background as GradientDrawable
        return if (text.isNotEmpty()) {
            drawable.setStroke(1, ContextCompat.getColor(context, R.color.grey))
            true
        } else {
            drawable.setStroke(1, ContextCompat.getColor(context, R.color.bg_btn))
            false
        }
    }

    private fun checkBox2(text: String): Boolean {
        val drawable: GradientDrawable = binding.box2.background as GradientDrawable
        return if (text.isNotEmpty()) {
            drawable.setStroke(1, ContextCompat.getColor(context, R.color.grey))
            true
        } else {
            drawable.setStroke(1, ContextCompat.getColor(context, R.color.bg_btn))
            false
        }
    }

    private fun checkBox3(text: String): Boolean {
        val drawable: GradientDrawable = binding.box3.background as GradientDrawable
        return if (text.isNotEmpty()) {
            drawable.setStroke(1, ContextCompat.getColor(context, R.color.grey))

            true
        } else {
            drawable.setStroke(1, ContextCompat.getColor(context, R.color.bg_btn))
            false
        }
    }

    private fun checkBox4(text: String): Boolean {
        val drawable: GradientDrawable = binding.box4.background as GradientDrawable
        return if (text.isNotEmpty()) {
            drawable.setStroke(1, ContextCompat.getColor(context, R.color.grey))
            true
        } else {
            drawable.setStroke(1, ContextCompat.getColor(context, R.color.bg_btn))
            false
        }
    }

    private fun checkBox5(text: String): Boolean {
        val drawable: GradientDrawable = binding.box5.background as GradientDrawable
        return if (text.isNotEmpty()) {
            drawable.setStroke(1, ContextCompat.getColor(context, R.color.grey))
            true
        } else {
            drawable.setStroke(1, ContextCompat.getColor(context, R.color.bg_btn))
            false
        }
    }

    private fun checkBox6(text: String): Boolean {
        val drawable: GradientDrawable = binding.box6.background as GradientDrawable
        return if (text.isNotEmpty()) {
            drawable.setStroke(1, ContextCompat.getColor(context, R.color.grey))
            true
        } else {
            drawable.setStroke(1, ContextCompat.getColor(context, R.color.bg_btn))
            false
        }
    }
}