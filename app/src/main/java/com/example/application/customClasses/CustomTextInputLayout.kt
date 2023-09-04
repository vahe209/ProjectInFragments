package com.example.application.customClasses

import android.content.Context
import android.content.res.ColorStateList
import android.graphics.Canvas
import android.util.AttributeSet
import android.view.View
import android.widget.TextView
import androidx.appcompat.content.res.AppCompatResources.getDrawable
import androidx.core.widget.addTextChangedListener
import com.example.application.R
import com.google.android.material.textfield.TextInputLayout

class CustomTextInputLayout(context: Context, attrs: AttributeSet) :
    TextInputLayout(context, attrs) {
    var errorTextGravity = TEXT_ALIGNMENT_TEXT_START
    var emptyStateMessage: Int = 0
    var isError: Boolean = false
    var isSucceed:Boolean = false

    init {
        context.theme.obtainStyledAttributes(
            attrs,
            R.styleable.CustomTextInputLayout,
            0, R.style.TextInputLayoutStyle
        ).apply {
            try {
                errorTextGravity = getInteger(
                    R.styleable.CustomTextInputLayout_errorTextGravity,
                    TEXT_ALIGNMENT_TEXT_START
                )
                emptyStateMessage = getResourceId(
                    R.styleable.CustomTextInputLayout_emptyStateErrorText,
                    0
                )
                val errorTextView =
                    this@CustomTextInputLayout.findViewById<TextView>(com.google.android.material.R.id.textinput_error)
                if (errorTextView != null) {
                    errorTextView.textAlignment = errorTextGravity
                }
            } finally {
                recycle()
            }
        }
    }

    override fun onLayout(changed: Boolean, left: Int, top: Int, right: Int, bottom: Int) {
        super.onLayout(changed, left, top, right, bottom)

        this.editText?.addTextChangedListener {
            val stateList = if (this.editText?.text.isNullOrEmpty()) {
                ColorStateList.valueOf(this.resources.getColor(R.color.accent_4))
            } else {
                ColorStateList.valueOf(this.resources.getColor(R.color.white))
            }
            setEndIconTintList(stateList)
        }
//        this.setEndIconOnClickListener {
//            if (endIconMode == END_ICON_CUSTOM) {
//                if (this.editText?.text?.isNotEmpty() == true) {
//                    this.editText?.text?.clear()
//                    this.editText?.clearFocus()
//                    val manager: InputMethodManager? = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager?
//                    manager?.hideSoftInputFromWindow(this.windowToken, 0)
//                }
//            }
//        }
    }


    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        if (error != null) {
            editText?.background = getDrawable(context, R.drawable.background_edit_text_error_state)
        } else {
            editText?.background =
                getDrawable(context, R.drawable.background_edit_text_main_white_active)
        }
    }

    override fun setEndIconVisible(visible: Boolean) {
        val isVisible = if (endIconMode == END_ICON_CUSTOM) true else visible
        super.setEndIconVisible(isVisible)
    }


    fun setErrorCondition(
        isError: Boolean = this.editText?.text.isNullOrEmpty(),
        errorText: CharSequence? = null) {
        if (visibility != View.VISIBLE) {
            this.isError = false
            return
        }
        val text = if (editText?.text.isNullOrEmpty() && emptyStateMessage != 0) {
            this.isError = true
            resources.getText(emptyStateMessage)
        } else {
            this.isError = isError
            errorText
        }
        error = if (this.isError)
            text else {
            isErrorEnabled = false
            null
        }
        clearFocus()
    }
}

fun CustomTextInputLayout.serErrorTextAlignment(alignment: Int) {
    errorTextGravity = alignment
}