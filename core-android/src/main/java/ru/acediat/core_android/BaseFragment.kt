package ru.acediat.core_android

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import androidx.viewbinding.ViewBinding

abstract class BaseFragment<B: ViewBinding, VM: ViewModel>: Fragment() {

    protected lateinit var binding: B
    protected abstract val viewModel: VM

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = instanceBinding(inflater, container)
        bindViews()
        inject()
        initViewModel()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        if(savedInstanceState != null)
            loadState(savedInstanceState)
        else
            refresh()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        saveState(outState)
    }

    protected abstract fun bindViews()

    protected abstract fun instanceBinding(inflater: LayoutInflater, container: ViewGroup?): B

    protected open fun saveState(outState: Bundle){}

    protected open fun loadState(savedInstanceState: Bundle){}

    protected open fun refresh(){}

    protected open fun inject(){}

    protected open fun initViewModel(){}
}