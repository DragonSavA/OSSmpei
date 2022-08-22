package ru.acediat.core_android.ext

import android.content.pm.PackageManager
import androidx.core.app.ActivityCompat
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

fun <T : RecyclerView.ViewHolder> Fragment.linearRecyclerView(
    adapter : RecyclerView.Adapter<T>
) : RecyclerView = RecyclerView(requireContext()).apply {
    layoutManager = LinearLayoutManager(context)
    this.adapter = adapter
}

fun Fragment.checkPermission(permission: String): Boolean = ActivityCompat.checkSelfPermission(
    requireContext(), permission
) == PackageManager.PERMISSION_GRANTED

fun Fragment.requestPermission(permission: String, requestCode: Int) = ActivityCompat.requestPermissions(
    requireActivity(), arrayOf(permission), requestCode
)