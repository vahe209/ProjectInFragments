package com.example.application.LoginAndRegister.viewModels

import androidx.lifecycle.ViewModel
import com.example.application.LoginAndRegister.data.CountryCodeItem

class ViewModelRegisterActivity:ViewModel() {
    private var  selectedNumberCodeLiveData= CountryCodeItem("US","+1", "🇺🇸","United States")
    fun setSelectedNumberCode(item: CountryCodeItem) {
        selectedNumberCodeLiveData = item
    }
    fun getSelectedNumberCode(): CountryCodeItem {
        return selectedNumberCodeLiveData
    }
}