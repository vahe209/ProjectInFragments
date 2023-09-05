package com.example.application.LoginAndRegister.data


import com.google.gson.annotations.SerializedName

data class CountryCodeItem(
    @SerializedName("code")
    val code: String,
    @SerializedName("dial_code")
    val dialCode: String,
    @SerializedName("flag")
    val flag: String,
    @SerializedName("name")
    val name: String,
    var isSelected: Boolean = false
)