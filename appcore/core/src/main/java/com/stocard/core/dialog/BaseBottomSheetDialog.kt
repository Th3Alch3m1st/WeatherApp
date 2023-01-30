package com.stocard.core.dialog

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import com.stocard.style.R
import com.stocard.core.fragment.FragmentCommunicator
import com.stocard.core.util.autoCleared
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

/**
 * Created By Rafiqul Hasan
 */
abstract class BaseBottomSheetDialog<DataBinding : ViewDataBinding> : BottomSheetDialogFragment() {
    protected var dataBinding: DataBinding by autoCleared()
    protected var fragmentCommunicator: FragmentCommunicator? = null
    /** Override to set layout resource id
     * @return layout resource id
     */
    @get:LayoutRes
    abstract val layoutResourceId: Int

    abstract val bottomSheetBehavior:Int

    override fun onAttach(context: Context) {
        super.onAttach(context)
        fragmentCommunicator = context as? FragmentCommunicator
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(STYLE_NORMAL, R.style.BottomSheetDialog)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        dataBinding = DataBindingUtil.inflate(inflater, layoutResourceId, container, false)
        dataBinding.lifecycleOwner = viewLifecycleOwner
        return dataBinding.root
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val dialog = super.onCreateDialog(savedInstanceState) as BottomSheetDialog
        dialog.setOnShowListener {
            val d = it as BottomSheetDialog
            val sheet = d.findViewById<View>(com.google.android.material.R.id.design_bottom_sheet)
            sheet?.let { view ->
                val behavior = BottomSheetBehavior.from(view)
                behavior.isHideable = true
                behavior.skipCollapsed = true
                behavior.state = bottomSheetBehavior
            }
        }
        return dialog
    }
}