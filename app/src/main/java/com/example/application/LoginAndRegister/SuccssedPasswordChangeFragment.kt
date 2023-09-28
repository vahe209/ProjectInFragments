package com.example.application.LoginAndRegister

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.Fragment
import com.example.application.databinding.FragmentSuccssedPasswordChangeBinding
import com.example.application.LoginAndRegister.interfaces.Interfaces


class SuccssedPasswordChangeFragment(
 private val createFragment: Interfaces.CreateFragment
) : Fragment() {
    private lateinit var binding: FragmentSuccssedPasswordChangeBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        binding = FragmentSuccssedPasswordChangeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.loginBtn.setOnClickListener {
            createFragment.createFragment(LoginFragment(createFragment))
        }
        val callback = object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                createFragment.createFragment(LoginFragment(createFragment))
            }
        }
        requireActivity().onBackPressedDispatcher.addCallback(callback)
    }

}