package com.example.application.LoginAndRegister

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import androidx.fragment.app.DialogFragment
import com.example.application.R
import com.example.application.databinding.FragmentTermsAndConditionsBinding
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class TermsAndConditionsFragment :  BottomSheetDialogFragment() {
    private lateinit var binding:FragmentTermsAndConditionsBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
       binding = FragmentTermsAndConditionsBinding.inflate(inflater,container,false)
        setStyle(DialogFragment.STYLE_NORMAL, R.style.CustomBottomSheetDialogTheme)
        dialog?.setOnShowListener { dialog ->
            val layout: FrameLayout? =
                (dialog as BottomSheetDialog).findViewById(com.google.android.material.R.id.design_bottom_sheet)
            layout?.let {
                val behavior = BottomSheetBehavior.from(it)
                behavior.state = BottomSheetBehavior.STATE_EXPANDED
                behavior.skipCollapsed = true
            }
        }
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.iconClose.setOnClickListener {
            dismiss()
        }
    }

}