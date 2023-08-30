package com.example.application

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.application.databinding.FragmentSetNewPasswordBinding

class SetNewPasswordFragment : Fragment() {
    private lateinit var binding : FragmentSetNewPasswordBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentSetNewPasswordBinding.inflate(inflater, container, false)
        return binding.root
    }

}