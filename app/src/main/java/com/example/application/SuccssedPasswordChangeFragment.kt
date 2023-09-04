package com.example.application

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.Fragment
import com.example.application.databinding.FragmentSuccssedPasswordChangeBinding
import com.example.application.interfaces.Interfaces


class SuccssedPasswordChangeFragment(
    private val context:Context,
private val createFragment: Interfaces.CreateFragment) : Fragment() {
private lateinit var binding: FragmentSuccssedPasswordChangeBinding
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = FragmentSuccssedPasswordChangeBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.btnLogin.setOnClickListener {
            createFragment.createFragment(LoginFragment(context,createFragment))
        }
        val callback = object: OnBackPressedCallback(true){
            override fun handleOnBackPressed() {
                createFragment.createFragment(LoginFragment(context,createFragment))
            }
        }
        requireActivity().onBackPressedDispatcher.addCallback(callback)
    }

}