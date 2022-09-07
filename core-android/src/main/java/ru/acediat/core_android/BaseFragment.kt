package ru.acediat.core_android

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.viewbinding.ViewBinding
import com.google.android.material.snackbar.Snackbar
import ru.acediat.core_android.ext.getColor
import ru.acediat.core_res.R as resR

abstract class BaseFragment<B: ViewBinding, VM: BaseViewModel>: Fragment() {

    protected lateinit var binding: B
    protected abstract val viewModel: VM

    private var snackBarAnchorView: View? = null

    protected abstract fun prepareViews(): Unit

    protected abstract fun instanceBinding(inflater: LayoutInflater, container: ViewGroup?): B

    protected open fun refresh(){}

    protected open fun inject(){}

    protected open fun prepareViewModel(){}

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = instanceBinding(inflater, container)
        if(requireActivity() is BottomNavigationHolder)
            snackBarAnchorView = (requireActivity() as BottomNavigationHolder).getBottomNavigationView()
        Logger.i(OSS_TAG, "${this.javaClass.simpleName} binding instanced")
        inject()
        Logger.i(OSS_TAG, "${this.javaClass.simpleName} injected")
        prepareViewModel()
        prepareViews()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        if(savedInstanceState != null)
            loadState(savedInstanceState)
        else
            refresh()
    }

    override fun onSaveInstanceState(outState: Bundle) = saveState(outState)

    protected fun showInfoSnackBar(view: View, message: String) = Snackbar
        .make(view, message, Snackbar.LENGTH_LONG).apply {
            snackBarAnchorView?.let{ this.anchorView = snackBarAnchorView }
            setBackgroundTint(getColor(resR.color.main_blue))
            setTextColor(getColor(resR.color.white))
            setActionTextColor(getColor(resR.color.bg_blue))
            setAction(requireActivity().getText(resR.string.ok)) {
                this.dismiss()
            }
        }.show()

    protected fun showErrorSnackBar(message: String){}

    private fun saveState(outState: Bundle) = viewModel.saveData(outState)

    private fun loadState(savedInstanceState: Bundle) = viewModel.restoreData(savedInstanceState)
}