package com.example.application

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.application.CreateEvent.*
import com.example.application.Downloads.DownloadsFragment
import com.example.application.LoginAndRegister.LoginFragment
import com.example.application.databinding.ActivityMainBinding
import com.example.application.LoginAndRegister.interfaces.Interfaces
import com.example.application.Search.SearchByDate
import com.example.application.Search.SearchFilterFragment

class MainActivity : AppCompatActivity(), Interfaces.CreateFragment {
    private lateinit var binding: ActivityMainBinding
    private var createFragment: Interfaces.CreateFragment = this

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        createFragment.createFragment(SearchByDate())
    }

    override fun createFragment(fragment: Fragment) {
        supportFragmentManager
            .beginTransaction()
            .replace(R.id.main_fragment_layout, fragment)
            .commit()
    }
}

