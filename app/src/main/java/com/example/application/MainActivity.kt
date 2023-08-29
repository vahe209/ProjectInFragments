package com.example.application

import android.content.Context
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.application.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        createFragment(this)
    }


    private fun createFragment(context: Context) {
        val ft = supportFragmentManager.beginTransaction().replace(R.id.main_fragment_layout, LoginFragment(context)).commit()
    }
}