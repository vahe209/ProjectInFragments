package com.example.application.util

import androidx.appcompat.widget.AppCompatEditText

const val PASSWORD_PATTERN = "^(?=.*\\d)(?=.*[a-z])(?=.*[A-Z])(?=.*[!@#$%^&*()_+~`<>?:{}])(?=\\S+$).{8,20}$"
const val EMAIL_PATTERN = "[a-zA-Z0-9\\+\\.\\_\\%\\-\\+]{1,256}\\@[a-zA-Z0-9][a-zA-Z0-9\\-]{0,64}(\\.[a-zA-Z0-9][a-zA-Z0-9\\-]{0,25})+"
const val PHONE_PATTERN = "[0-9]{10,10}"

fun AppCompatEditText.checkPattern(pattern: String): Boolean {
    return this.text?.trim()?.matches(pattern.toRegex()) == true
}

fun String?.checkPattern(pattern: String): Boolean {
    return this?.trim()?.matches(pattern.toRegex()) == true
}