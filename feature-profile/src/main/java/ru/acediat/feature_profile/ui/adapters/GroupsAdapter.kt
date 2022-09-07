package ru.acediat.feature_profile.ui.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import ru.acediat.core_android.AdapterCallback
import ru.acediat.core_android.RecyclerViewAdapter
import ru.acediat.feature_profile.databinding.ItemGroupBinding

class GroupsAdapter: RecyclerViewAdapter<String, ItemGroupBinding>() {

    private var onGroupClick: (item: String) -> Unit = {}
    private var onDeleteClick: (item: String) -> Unit = {}

    fun setOnGroupClick(onClick: (item: String) -> Unit){
        onGroupClick = onClick
    }

    fun setOnDeleteClick(onClick: (item: String) -> Unit){
        onDeleteClick = onClick
    }

    fun containGroup(group: String) = items.contains(group)

    override fun onCreateViewHolder(
        parent: ViewGroup,
        position: Int
    ): ViewHolder<String, ItemGroupBinding> = ViewHolder(
        ItemGroupBinding.inflate(LayoutInflater.from(parent.context), parent, false),
        object : AdapterCallback<String, ItemGroupBinding>{
            override fun bindViews(binding: ItemGroupBinding, item: String, position: Int) {
                binding.group.text = item
                binding.group.setOnClickListener { onGroupClick(item) }
                binding.deleteButton.setOnClickListener { onDeleteClick(item) }
            }

            override fun onViewClicked(view: View, item: String) {}
        }
    )
}