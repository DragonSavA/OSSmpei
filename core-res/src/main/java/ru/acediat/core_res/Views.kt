package ru.acediat.core_res

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup


fun loadingFrame(inflater: LayoutInflater, container : ViewGroup) : View = inflater.inflate(
    R.layout.frame_loading, container, false
)