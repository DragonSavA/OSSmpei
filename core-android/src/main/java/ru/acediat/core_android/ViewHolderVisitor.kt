package ru.acediat.core_android

import androidx.databinding.ViewDataBinding

interface ViewHolderVisitor {

    val layout: Int
    fun acceptBinding(item: Any): Boolean
    fun bind(binding: ViewDataBinding, item: Any)

}