package com.example.application

import android.annotation.SuppressLint
import android.content.Context
import android.content.res.ColorStateList
import android.os.Bundle
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
import com.example.application.adapter.CodesAdapter
import com.example.application.data.PhoneCodesItem
import com.example.application.databinding.FragmentRegisterBinding
import com.example.application.interfaces.Interfaces
import com.example.application.viewModels.ViewModelRegisterActivity
import java.util.regex.Matcher
import java.util.regex.Pattern

class RegisterFragment(
    private val context: Context,
    private val createFragment: Interfaces.CreateFragment
) :
    Fragment(),
    WrongDataFragment.FragmentInteractionListener,
    CodesAdapter.CloseFragment {
    private lateinit var binding: FragmentRegisterBinding
    private var validPassword: Boolean = false
    private var isChecked: Boolean = false
    private lateinit var viewModel: ViewModelRegisterActivity
    private lateinit var selectedNumberCode: PhoneCodesItem
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
            binding.flag.text = arguments!!.getString("flag")
            binding.numberCodeFixed.text = arguments!!.getString("numberCode")
        }
        binding.toolbar.iconBack.setOnClickListener {
            createFragment.createFragment(LoginFragment(context, createFragment))
        }
        binding.chooseCountryCode.setOnClickListener {
            selectedNumberCode = viewModel.getSelectedNumberCode()
            fragmentEnterNumberCode = EnterNumberCodeFragment(selectedNumberCode, this)
            openFragment(fragmentEnterNumberCode)
        }
        binding.textForLogin.setOnClickListener {
            createFragment.createFragment(LoginFragment(context, createFragment))
        }
        binding.passwordEdit.doOnTextChanged { text, _, _, _ ->
            if (isValidPass(text.toString())) {
                binding.passwordInputLayout.setErrorTextColor(ColorStateList.valueOf(resources.getColor(R.color.accent_7)))
                binding.passwordInputLayout.error = "Excellent"
                validPassword = true
            } else {
                binding.passwordInputLayout.setErrorTextColor(ColorStateList.valueOf(resources.getColor(R.color.accent_2)))
                binding.passwordInputLayout.error = null
                validPassword = false
            }
        }
        binding.confirmEdit.doOnTextChanged { text, _, _, _ ->
            if (text.toString() == binding.passwordEdit.text.toString() && binding.passwordEdit.text.toString()
                    .isNotEmpty()
            ) {
                binding.confirmInputLayout.setErrorTextColor(ColorStateList.valueOf(resources.getColor(R.color.accent_7)))
                binding.confirmInputLayout.apply {
                    binding.confirmInputLayout.error = "Values match"
                }
            } else {
                binding.confirmInputLayout.apply {
                    binding.confirmInputLayout.setErrorTextColor(ColorStateList.valueOf(resources.getColor(R.color.accent_2)))
                    error = "Values do not match"
                }
            }
        }
        binding.agreementCheckbox.setOnCheckedChangeListener { _, isChecked ->
            this.isChecked = isChecked
        }
        binding.btnJoin.setOnClickListener {
            val isFistNameValid = checkFirstName(binding.fNameEdit.text.toString())
            val isLastNameValid = checkLastName(binding.lNameEdit.text.toString())
            val isEmailValid = checkEmail(binding.emailEdit.text.toString())
            val isPhoneValid = checkPhone(binding.phoneEdit.text.toString())
            val isPasswordValid = checkPass(binding.passwordEdit.text.toString())
            val isConfirmPassValid = checkConfirmPass(binding.confirmEdit.text.toString())
            val isCheckBoxChecked = checkBoxIsChecked()
            if (!isFistNameValid ||
                !isLastNameValid ||
                !isEmailValid ||
                !isPhoneValid ||
                !isPasswordValid ||
                !isConfirmPassValid ||
                !isCheckBoxChecked ||
                binding.passwordEdit.text.toString() != binding.confirmEdit.text.toString()
            ) {
                openErrorFragment()
            }
            if (isFistNameValid &&
                isLastNameValid &&
                isEmailValid &&
                isPhoneValid &&
                isPasswordValid &&
                isConfirmPassValid &&
                isCheckBoxChecked &&
                binding.passwordEdit.text.toString() == binding.confirmEdit.text.toString()
            ) {
                // TODO:  registerCall
                Toast.makeText(context, "Everything is working", Toast.LENGTH_SHORT).show()
            }
        }
        val callback = object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                createFragment.createFragment(LoginFragment(context, createFragment))
            }

        }
        requireActivity().onBackPressedDispatcher.addCallback(callback)
        binding.agreementText2.setOnClickListener {
            openFragment(TermsAndConditionsFragment())
        }
    }

    private fun checkBoxIsChecked(): Boolean {
        return if (isChecked) {
            binding.agreementText1.setTextColor(ContextCompat.getColor(context, R.color.white))
            binding.agreementText2.setTextColor(ContextCompat.getColor(context, R.color.accent_5))
            true
        } else {
            binding.agreementText1.setTextColor(ContextCompat.getColor(context, R.color.accent_2))
            binding.agreementText2.setTextColor(ContextCompat.getColor(context, R.color.accent_2))
            false
        }
    }

    @SuppressLint("SetTextI18n")
    private fun checkConfirmPass(confPass: String): Boolean {
        return if (confPass.isNotEmpty()) {
            binding.confirmInputLayout.error = null
            true
        } else {
            binding.confirmInputLayout.error = "Confirm password"
            false
        }
    }

    @SuppressLint("SetTextI18n")
    private fun checkPass(password: String): Boolean {
        validPassword = isValidPass(password)
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

    @SuppressLint("SetTextI18n")
    private fun checkEmail(email: String): Boolean {
        return if (email.isNotEmpty()) {
            val regex = "^[A-Za-z\\d+_.-]+@(.+)$"
            val pattern = Pattern.compile(regex)
            val matcher = pattern.matcher(email)
            if (matcher.matches()) {
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
        binding.toolbar.iconBack.isVisible = false
        binding.toolbar.toolbarTitleTv.isVisible = false
        val wrongDataFragment =
            WrongDataFragment(resources.getString(R.string.invalid_email_or_phone_key))
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

    override fun closeFragment(flag: String, numberCode: String, selectedItem: PhoneCodesItem) {
        fragmentEnterNumberCode.dismiss()
        viewModel.setSelectedNumberCode(selectedItem)
        binding.flag.text = flag
        binding.numberCodeFixed.text = numberCode
    }
}
