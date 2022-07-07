package ru.acediat.feature_timetable

import androidx.databinding.ViewDataBinding
import ru.acediat.core_android.ViewHolderVisitor
import ru.acediat.core_res.R
import ru.acediat.core_res.databinding.ItemEventBinding
import ru.acediat.feature_timetable.entities.Lesson

class LessonViewHolder : ViewHolderVisitor {

    override val layout: Int = R.layout.item_event

    override fun acceptBinding(item: Any): Boolean = item is Lesson

    override fun bind(binding: ViewDataBinding, item: Any) = with(binding as ItemEventBinding){
        val lesson = item as Lesson
        eventTime.text = lesson.getFormatTime()
        eventType.text = lesson.type
        eventName.text = lesson.name
        eventPlace.text = lesson.place
        eventOwnerName.text = lesson.teacher
    }
}