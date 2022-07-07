package ru.acediat.core_android

import androidx.recyclerview.widget.DiffUtil

class BaseDiffCallback : DiffUtil.ItemCallback<HasId>() {
    override fun areItemsTheSame(oldItem: HasId, newItem: HasId): Boolean = oldItem.id == newItem.id
    override fun areContentsTheSame(oldItem: HasId, newItem: HasId): Boolean = oldItem == newItem
}