package com.stocard.core.fragment

import android.content.Context
import android.os.Bundle
import android.view.*
import androidx.annotation.LayoutRes
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import com.stocard.core.util.autoCleared

/**
 * Created by Rafiqul Hasan
 */
abstract class BaseFragment<DataBinding : ViewDataBinding> : Fragment() {
    protected var fragmentCommunicator: FragmentCommunicator? = null
    protected var dataBinding: DataBinding by autoCleared()

    /** Override to set layout resource id
     * @return layout resource id
     */
    @get:LayoutRes
    abstract val layoutResourceId: Int

    override fun onAttach(context: Context) {
        super.onAttach(context)
        fragmentCommunicator = context as? FragmentCommunicator
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        dataBinding = DataBindingUtil.inflate(inflater, layoutResourceId, container, false)
        dataBinding.lifecycleOwner = viewLifecycleOwner
        return dataBinding.root
    }
}