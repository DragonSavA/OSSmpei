package ru.acediat.core_android.ext

import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

fun <T : RecyclerView.ViewHolder> Fragment.linearRecyclerView(
    adapter : RecyclerView.Adapter<T>
) : RecyclerView = RecyclerView(requireContext()).apply {
    layoutManager = LinearLayoutManager(context)
    this.adapter = adapter
}