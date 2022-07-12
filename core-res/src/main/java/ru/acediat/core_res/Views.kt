package ru.acediat.core_res

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup


fun loadingFrame(container : ViewGroup) : View = LayoutInflater.from(container.context).inflate(
    R.layout.frame_loading, container, false
)