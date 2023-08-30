package com.example.application

import android.content.Context
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.application.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity(), CreateFragment {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        createLoginFragment()
    }

    private fun createLoginFragment() {
             supportFragmentManager
            .beginTransaction()
            .replace(R.id.main_fragment_layout, LoginFragment(this,this))
            .commit()
    }

    override fun createFragment(fragment: Fragment) {
        supportFragmentManager
            .beginTransaction()
            .replace(R.id.main_fragment_layout, fragment)
            .commit()
    }
}

interface CreateFragment{
    fun createFragment(fragment:Fragment)
}