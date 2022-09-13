package ru.acediat.core_android

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.viewbinding.ViewBinding
import javax.inject.Inject

abstract class BaseFragment<B: ViewBinding, VM: BaseViewModel>: Fragment() {

    @Inject lateinit var notificator: Notificator

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

    //TODO: заменить на не устаревший способ
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        if(requireActivity() is BottomNavigationHolder)
            snackBarAnchorView = (requireActivity() as BottomNavigationHolder).getBottomNavigationView()
    }

    override fun onSaveInstanceState(outState: Bundle) = saveState(outState)

    protected fun showInfoSnackBar(view: View, message: String) =
        notificator.showInfo(view, message, snackBarAnchorView)

    protected fun showErrorSnackBar(view: View, message: String) = notificator.showError(view, message, snackBarAnchorView)

    private fun saveState(outState: Bundle) = viewModel.saveData(outState)

    private fun loadState(savedInstanceState: Bundle) = viewModel.restoreData(savedInstanceState)
}