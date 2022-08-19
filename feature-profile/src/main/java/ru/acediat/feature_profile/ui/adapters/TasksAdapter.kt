package ru.acediat.feature_profile.ui.adapters

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.content.res.AppCompatResources
import ru.acediat.core_android.AdapterCallback
import ru.acediat.core_android.RecyclerViewAdapter
import ru.acediat.feature_profile.R
import ru.acediat.feature_profile.databinding.ItemTaskBinding
import ru.acediat.feature_profile.model.dtos.*
import ru.acediat.core_res.R as resR

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
                if(item.status == PAYED && item.adminComment != null) {
                    addBottomAddition(container, item, resR.drawable.shape_green_rectangle)
                }else if(
                    (item.status == PENALIZED || item.status == REFUSED)
                    && item.adminComment != null
                ){
                    addBottomAddition(container, item, resR.drawable.shape_red_rectangle)
                }
            }

            override fun onViewClicked(view: View, item: TaskDTO) = onTaskClick(item)

        }
    )

    @SuppressLint("SetTextI18n")
    private fun addBottomAddition(container: ViewGroup, item: TaskDTO, background: Int){
        container.background = AppCompatResources.getDrawable(container.context, background)
        container.addView(
            TextView(container.context).apply {
                val dp = container.context.resources.displayMetrics.density
                layoutParams = LinearLayout.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.MATCH_PARENT
                ).apply {
                    val verticalMargin = (dp*8).toInt()
                    val horizontalMargin = (dp*12).toInt()
                    setMargins(verticalMargin, verticalMargin, horizontalMargin, horizontalMargin)
                }
                setTextAppearance(resR.style.SmallMainText)
                text = container.context.getString(R.string.admin_comment) + "\n" + item.adminComment
            }
        )
    }
}