package ru.acediat.core_android

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.viewbinding.ViewBinding

abstract class BaseFragment<B: ViewBinding, VM: BaseViewModel>: Fragment() {

    protected lateinit var binding: B
    protected abstract val viewModel: VM

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = instanceBinding(inflater, container)
        inject()
        prepareViews()
        prepareViewModel()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        if(savedInstanceState != null)
            loadState(savedInstanceState)
        else
            refresh()
    }

    override fun onSaveInstanceState(outState: Bundle) = saveState(outState)

    protected abstract fun prepareViews()

    protected abstract fun instanceBinding(inflater: LayoutInflater, container: ViewGroup?): B

    protected open fun refresh(){}

    protected open fun inject(){}

    protected open fun prepareViewModel(){}

    private fun saveState(outState: Bundle) = viewModel.saveData(outState)

    private fun loadState(savedInstanceState: Bundle) = viewModel.restoreData(savedInstanceState)
}