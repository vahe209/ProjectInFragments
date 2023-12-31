package com.example.application.LoginAndRegister

import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.application.LoginAndRegister.adapter.CodesAdapter
import com.example.application.LoginAndRegister.data.CountryCodeItem
import com.example.application.LoginAndRegister.interfaces.Interfaces
import com.example.application.LoginAndRegister.viewModels.ViewModelRegisterActivity
import com.example.application.R
import com.example.application.databinding.FragmentRegisterBinding
import com.example.application.util.EMAIL_PATTERN
import com.example.application.util.PASSWORD_PATTERN
import com.example.application.util.checkPattern
import java.util.regex.Matcher
import java.util.regex.Pattern

class RegisterFragment(private val createFragment: Interfaces.CreateFragment
) : Fragment(), WrongDataFragment.FragmentInteractionListener, CodesAdapter.CloseFragment {
    private lateinit var binding: FragmentRegisterBinding
    private var validPassword: Boolean = false
    private var isChecked: Boolean = false
    private lateinit var viewModel: ViewModelRegisterActivity
    private lateinit var selectedNumberCode: CountryCodeItem
    private lateinit var fragmentEnterNumberCode: EnterNumberCodeFragment

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        binding = FragmentRegisterBinding.inflate(inflater, container, false)
        binding.toolbar.toolbarTitleTv.text =
            resources.getText(R.string.toolbar_title_in_register_key)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(this)[ViewModelRegisterActivity::class.java]
        if (arguments != null) {
            binding.flagTv.text = arguments?.getString("flag")
            binding.phoneInputLayout.prefixText = arguments?.getString("numberCode")
        }
        binding.toolbar.iconBackImg.setOnClickListener {
            createFragment.createFragment(LoginFragment(createFragment))
        }
        binding.chooseCountryCode.setOnClickListener {
            selectedNumberCode = viewModel.getSelectedNumberCode()
            fragmentEnterNumberCode = EnterNumberCodeFragment(selectedNumberCode, this)
            openFragment(fragmentEnterNumberCode)
        }
        binding.forLoginTv.setOnClickListener {
            createFragment.createFragment(LoginFragment(createFragment))
        }
        binding.passwordEdt.doOnTextChanged { text, _, _, _ ->
            validPassword = text?.toString().checkPattern(PASSWORD_PATTERN)
            binding.passwordInputLayout.checkSuccessCondition("Excellent", validPassword)
        }
        binding.confirmEdt.doOnTextChanged { text, _, _, _ ->
            if (text.toString() == binding.passwordEdt.text.toString() && binding.passwordEdt.text.toString()
                    .isNotEmpty()
            ) {
                binding.confirmInputLayout.checkSuccessCondition(
                    "Values match", text.toString() == binding.passwordEdt.text.toString()
                )
            } else {
                binding.confirmInputLayout.error = "Values do not match"
            }
        }
        binding.agreementCheckboxBtn.setOnCheckedChangeListener { _, isChecked ->
            this.isChecked = isChecked
        }
        binding.joinBtn.setOnClickListener {
            val isFistNameValid = checkFirstName(binding.fNameEdt.text.toString())
            val isLastNameValid = checkLastName(binding.lNameEdt.text.toString())
            val isEmailValid = checkEmail(binding.emailEdt.text.toString())
            val isPhoneValid = checkPhone(binding.phoneEdt.text.toString())
            val isPasswordValid = checkPass(binding.passwordEdt.text.toString())
            val isConfirmPassValid = checkConfirmPass(binding.confirmEdt.text.toString())
            val isCheckBoxChecked = checkBoxIsChecked()
            if (!isFistNameValid ||
                !isLastNameValid ||
                !isEmailValid ||
                !isPhoneValid ||
                !isPasswordValid ||
                !isConfirmPassValid ||
                !isCheckBoxChecked ||
                binding.passwordEdt.text.toString() != binding.confirmEdt.text.toString()) {
                openErrorFragment()
            }
            if (isFistNameValid &&
                isLastNameValid &&
                isEmailValid &&
                isPhoneValid &&
                isPasswordValid &&
                isConfirmPassValid &&
                isCheckBoxChecked &&
                binding.passwordEdt.text.toString() == binding.confirmEdt.text.toString()) {
                // TODO:  registerCall
                Toast.makeText(context, "Everything is working", Toast.LENGTH_SHORT).show()
            }
        }
        val callback = object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                createFragment.createFragment(LoginFragment(createFragment))
            }
        }
        requireActivity().onBackPressedDispatcher.addCallback(callback)
        binding.agreementTv2.setOnClickListener {
            openFragment(TermsAndConditionsFragment())
        }
    }

    private fun checkBoxIsChecked(): Boolean {
        return if (isChecked) {
            binding.agreementTv1.setTextColor(resources.getColor(R.color.white))
            binding.agreementTv2.setTextColor(resources.getColor(R.color.accent_5))
            true
        } else {
            binding.agreementTv1.setTextColor(resources.getColor(R.color.accent_2))
            binding.agreementTv2.setTextColor(resources.getColor(R.color.accent_2))
            false
        }
    }

    @SuppressLint("SetTextI18n")
    private fun checkConfirmPass(confPass: String): Boolean {
        return if (confPass.isNotEmpty()) {
            true
        } else {
            binding.confirmInputLayout.error = "Confirm password"
            false
        }
    }

    @SuppressLint("SetTextI18n")
    private fun checkPass(password: String): Boolean {
        validPassword = password.checkPattern(PASSWORD_PATTERN)
        return if (password.isNotEmpty()) {
            if (validPassword) {
                true
            } else {
                binding.passwordInputLayout.error = "Incorrect password type"
                false
            }
        } else {
            binding.passwordInputLayout.error = "Password is required"
            false
        }
    }

    @SuppressLint("SetTextI18n")
    private fun checkPhone(phone: String): Boolean {
        return if (phone.isNotEmpty()) {
            binding.phoneInputLayout.error = null
            true
        } else {
            binding.phoneInputLayout.error = "Phone is required"
            false
        }
    }

    @SuppressLint("SetTextI18n")
    private fun checkLastName(lastName: String): Boolean {
        return if (lastName.isNotEmpty()) {
            binding.lNameInputLayout.error = null
            true
        } else {
            binding.lNameInputLayout.error = "Last name is required"
            false
        }
    }

    @SuppressLint("SetTextI18n")
    private fun checkFirstName(firstName: String): Boolean {
        return if (firstName.isNotEmpty()) {
            binding.fNameInputLayout.error = null
            true
        } else {
            binding.fNameInputLayout.error = "First name is required"
            false
        }
    }

    @SuppressLint("SetTextI18n")
    private fun checkEmail(email: String): Boolean {
        return if (email.isNotEmpty()) {
            val validEmail = email.checkPattern(EMAIL_PATTERN)
            if (validEmail) {
                binding.emailInputLayout.error = null
                true
            } else {
                binding.emailInputLayout.error = "Incorrect Email type"
                false
            }
        } else {
            binding.emailInputLayout.error = "Email is required"
            false
        }
    }

    private fun openFragment(fragment: Fragment) {
        requireActivity().supportFragmentManager.beginTransaction().add(fragment, "xX").commit()
    }

    private fun openErrorFragment() {
        binding.toolbar.iconBackImg.isVisible = false
        binding.toolbar.toolbarTitleTv.isVisible = false
        val wrongDataFragment =
            WrongDataFragment(resources.getString(R.string.invalid_email_or_phone_key))
        wrongDataFragment.setFragmentInteractionListener(this)
        activity?.supportFragmentManager?.beginTransaction()
            ?.replace(R.id.toolbar, wrongDataFragment)?.commit()
    }

    override fun onCloseButtonPressed() {
        binding.toolbar.iconBackImg.isVisible = true
        binding.toolbar.toolbarTitleTv.isVisible = true
        val fragment = activity?.supportFragmentManager?.findFragmentById(R.id.toolbar)
        if (fragment is WrongDataFragment) {
            activity?.supportFragmentManager?.beginTransaction()?.remove(fragment)?.commit()
        }
    }

    override fun closeFragment(flag: String, numberCode: String, selectedItem: CountryCodeItem) {
        fragmentEnterNumberCode.dismiss()
        viewModel.setSelectedNumberCode(selectedItem)
        binding.flagTv.text = flag
        binding.phoneInputLayout.prefixText = numberCode
    }
}
