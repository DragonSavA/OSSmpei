package ru.acediat.feature_timetable

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import ru.acediat.core_android.BaseDiffCallback
import ru.acediat.core_android.HasId
import ru.acediat.core_android.ViewHolderVisitor
import ru.acediat.core_android.ViewHoldersManager
import javax.inject.Inject

class EventsAdapter @Inject constructor(
    private val viewHoldersManager: ViewHoldersManager
) : ListAdapter<HasId, EventsAdapter.DataViewHolder>(BaseDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DataViewHolder =
        LayoutInflater.from(parent.context).run {
            val holder = viewHoldersManager.getViewHolder(viewType)
            DataViewHolder(DataBindingUtil.inflate(this, holder.layout, parent, false), holder)
        }

    override fun onBindViewHolder(holder: DataViewHolder, position: Int) = holder.bind(getItem(position))

    override fun getItemViewType(position: Int): Int = viewHoldersManager.getItemType(getItem(position))

    class DataViewHolder(
        private val binding : ViewDataBinding,
        private val holder : ViewHolderVisitor
    ) : RecyclerView.ViewHolder(binding.root){

        fun bind(item : Any) = holder.bind(binding, item)

    }
}