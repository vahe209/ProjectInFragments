package com.example.application.util

import android.content.Context
import android.content.res.ColorStateList
import android.util.AttributeSet
import android.view.View
import android.widget.TextView
import com.example.application.R
import com.google.android.material.textfield.TextInputLayout

class CustomTextInputLayout(context: Context, attrs: AttributeSet) :
    TextInputLayout(context, attrs) {
    private var errorTextGravity = TEXT_ALIGNMENT_VIEW_END
    private var emptyStateMessage: Int = 0
    private var isError: Boolean = false
    private var isSuccess: Boolean = false
    private var errorTextView: TextView?

    init {
        context.theme.obtainStyledAttributes(
            attrs, R.styleable.CustomTextInputLayout, 0, R.style.TextInputLayoutStyle
        ).apply {
            try {
                errorTextGravity = getInteger(
                    R.styleable.CustomTextInputLayout_errorTextGravity, TEXT_ALIGNMENT_VIEW_END
                )
                emptyStateMessage = getResourceId(
                    R.styleable.CustomTextInputLayout_emptyStateErrorText, 0
                )
                errorTextView =
                    this@CustomTextInputLayout.findViewById(com.google.android.material.R.id.textinput_error)
                if (errorTextView != null) {
                    errorTextView?.textAlignment = errorTextGravity
                }
            } finally {
                recycle()
            }
        }
    }

    override fun onLayout(changed: Boolean, left: Int, top: Int, right: Int, bottom: Int) {
        super.onLayout(changed, left, top, right, bottom)

    }

    /*    override fun setEndIconVisible(visible: Boolean) {
            val isVisible = if (endIconMode == END_ICON_CUSTOM) true else visible
            super.setEndIconVisible(isVisible)
        }*/

    fun checkErrorCondition(
        condition: Boolean = this.editText?.text.isNullOrEmpty(), errorText: CharSequence? = null
    ) {
        if (visibility != View.VISIBLE) {
            this.isError = false
            return
        }
        val text = if (editText?.text.isNullOrEmpty() && emptyStateMessage != 0) {
            this.isError = true
            resources.getText(emptyStateMessage)
        } else {
            this.isError = condition
            errorText
        }
        error = if (this.isError) {
            text
        } else {
            null
        }
        clearFocus()
    }

    fun checkSuccessCondition(
        text: CharSequence, condition: Boolean
    ) {
        if (condition && !isSuccess) {
            setErrorTextColor(ColorStateList.valueOf(this.resources.getColor(R.color.accent_7)))
            boxStrokeErrorColor = ColorStateList.valueOf(this.resources.getColor(R.color.accent_7))
            error = text
        }
        isSuccess = condition
        if (!condition) {
            error = null
            errorTextView?.visibility = View.GONE
            setErrorTextColor(ColorStateList.valueOf(this.resources.getColor(R.color.accent_2)))
            boxStrokeErrorColor = ColorStateList.valueOf(this.resources.getColor(R.color.accent_2))
        }
    }

    fun serErrorTextAlignment(alignment: Int) {
        errorTextGravity = alignment
        errorTextView?.textAlignment = errorTextGravity
    }
}
