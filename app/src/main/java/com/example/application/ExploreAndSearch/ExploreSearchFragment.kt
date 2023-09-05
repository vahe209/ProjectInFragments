package com.example.application.ExploreAndSearch

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import com.example.application.databinding.FragmentExploreSearchBinding

class ExploreSearchFragment : Fragment() {
    private lateinit var binding: FragmentExploreSearchBinding
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentExploreSearchBinding.inflate(inflater, container, false)
        binding.toolbar.toolbarTitleTv.text = "EXPLORE"
        binding.toolbar.toolbarSomeBtnTv.text = "Filter"
        binding.toolbar.toolbarSomeBtnTv.isVisible = true
        return binding.root
    }
}