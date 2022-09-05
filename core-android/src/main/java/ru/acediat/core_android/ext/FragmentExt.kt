package ru.acediat.core_android.ext

import android.content.pm.PackageManager
import android.view.View
import androidx.annotation.ColorRes
import androidx.core.app.ActivityCompat
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.google.android.material.snackbar.Snackbar

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

fun Fragment.refreshLayout(
    vararg childs: View, onRefresh: () -> Unit
) = SwipeRefreshLayout(requireContext()).apply{
    for(child in childs){
        addView(child)
    }
    setOnRefreshListener { onRefresh() }
}
