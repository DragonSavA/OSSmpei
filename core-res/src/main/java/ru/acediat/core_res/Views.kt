package ru.acediat.core_res

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.appcompat.content.res.AppCompatResources
import androidx.core.view.isVisible
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import ru.acediat.core_res.databinding.LayoutNotifyScreenBinding

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

fun notifyScreen(
    parent: ViewGroup,
    @DrawableRes drawableId: Int,
    @StringRes messageId: Int,
    @StringRes descriptionId: Int = -1,
    @StringRes buttonTitleId: Int? = null,
    onClick: (View) -> Unit = {},
): View = with(
    LayoutNotifyScreenBinding.inflate(
        LayoutInflater.from(parent.context), parent, false
    )
){
    image.setImageDrawable(AppCompatResources.getDrawable(parent.context, drawableId))
    notifyTitle.text = parent.context.getString(messageId)
    if(descriptionId != -1)
        description.text = parent.context.getString(descriptionId)
    else
        description.isVisible = false
    buttonTitleId?.let{
        button.text = parent.context.getString(buttonTitleId)
        button.setOnClickListener { onClick(it) }
    } ?: run{
        button.isVisible = false
    }
    root
}