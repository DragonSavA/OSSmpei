package ru.acediat.feature_profile.ui.adapters

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import ru.acediat.core_android.AdapterCallback
import ru.acediat.core_android.RecyclerViewAdapter
import ru.acediat.feature_profile.R
import ru.acediat.feature_profile.databinding.ItemTaskBinding
import ru.acediat.feature_profile.model.dtos.TaskDTO

class TasksAdapter: RecyclerViewAdapter<TaskDTO, ItemTaskBinding>() {

    private var onTaskClick: (task: TaskDTO) -> Unit = {}

    fun setOnTaskClickListener(listener: (task: TaskDTO) -> Unit){
        onTaskClick = listener
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        position: Int
    ): ViewHolder<TaskDTO, ItemTaskBinding> = ViewHolder(
        ItemTaskBinding.inflate(LayoutInflater.from(parent.context), parent, false),
        object : AdapterCallback<TaskDTO, ItemTaskBinding> {

            @SuppressLint("SetTextI18n")
            override fun bindViews(binding: ItemTaskBinding, item: TaskDTO, position: Int) = with(binding) {
                shortDescription.text = item.shortDescription
                description.text = item.fullDescription
                deadlines.text = item.startDate + " - " + item.endDate
                reward.text = root.context.getString(R.string.reward) + " " + item.reward
            }

            override fun onViewClicked(view: View, item: TaskDTO) = onTaskClick(item)

        }
    )
}