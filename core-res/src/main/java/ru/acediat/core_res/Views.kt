package ru.acediat.core_res

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView


fun loadingFrame(container : ViewGroup) : View = LayoutInflater.from(container.context).inflate(
    R.layout.frame_loading, container, false
)

fun linearRecyclerView(
    context : Context,
) = RecyclerView(context).apply {
    layoutManager = LinearLayoutManager(context)
}

fun gridRecyclerView(
    context : Context,
    spanCount: Int
) = RecyclerView(context).apply {
    layoutManager = GridLayoutManager(context, spanCount)
}