package com.example.application.viewModels

import androidx.lifecycle.ViewModel
import com.example.application.data.PhoneCodesItem

class ViewModelRegisterActivity:ViewModel() {
    private var  selectedNumberCodeLiveData= PhoneCodesItem("US","+1", "🇺🇸","United States")
    fun setSelectedNumberCode(item: PhoneCodesItem) {
        selectedNumberCodeLiveData = item
    }
    fun getSelectedNumberCode(): PhoneCodesItem {
        return selectedNumberCodeLiveData
    }
}