package ru.acediat.feature_timetable

import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import ru.acediat.core_android.BasePagerAdapter
import ru.acediat.core_android.ext.inflater
import ru.acediat.core_res.loadingFrame
import ru.acediat.core_res.linearRecyclerView
import ru.acediat.core_res.notifyScreen
import ru.acediat.feature_timetable.databinding.ScreenUnknownGroupBinding
import ru.acediat.core_res.R as resR
import java.time.LocalDateTime
import javax.inject.Inject

class DaysAdapter @Inject constructor(
    private val datePicker: DatePicker,
) : BasePagerAdapter<Timetable>() {

    private var onRefresh: () -> Unit = {}
    private var groupSetCallback: (String) -> Unit = {}

    var selectedDate : LocalDateTime = datePicker.getCurrentDate()
        private set

    var isEmptyGroup: Boolean = false

    fun changeDays(date : LocalDateTime){
        datePicker.setDates(date)
        selectedDate = date
        notifyDataSetChanged()
    }

    fun setGroupSetCallback(callback: (String) -> Unit){
        groupSetCallback = callback
    }

    fun setOnRefreshListener(onRefresh: () -> Unit){
        this.onRefresh = onRefresh
    }

    fun getLastDate() = datePicker.getDate(count-1)

    fun getFirstDate() = datePicker.getDate(0)

    override fun getCount(): Int = datePicker.amountOfDays

    override fun instantiateItem(container: ViewGroup, position: Int) =
        if(isEmptyGroup)
            instantiateChooseGroup(container)
        else if(data == null)
            instantiateLoading(container)
        else if(data!![position].isEmpty())
            instantiateEmptyDay(container)
        else
            instantiateDayTimetable(container, position)

    override fun getPageTitle(position: Int): CharSequence = datePicker.getTimetableFormatDate(position)

    override fun instantiateLoading(container : ViewGroup) : View = with(loadingFrame(container)) {
        container.addView(this)
        return this
    }

    private fun instantiateEmptyDay(container: ViewGroup) : View = with(
        SwipeRefreshLayout(container.context).apply {
            addView(notifyScreen(
                container, R.drawable.ic_education,
                R.string.self_education_day, R.string.self_education_descripton
            ))
            setOnRefreshListener { onRefresh() }
        }
    ){
        container.addView(this)
        return@with this
    }

    private fun instantiateDayTimetable(container : ViewGroup, position: Int) : View = with(
        SwipeRefreshLayout(container.context)
    ){
        layoutParams = ViewGroup.LayoutParams(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.MATCH_PARENT
        )
        addView(linearRecyclerView(context).apply {
            adapter = LessonsAdapter().apply {
                addItems(data!![position])
            }
        })
        setOnRefreshListener { onRefresh() }
        container.addView(this)
        return@with this
    }

    private fun instantiateChooseGroup(container: ViewGroup): View = with(
        ScreenUnknownGroupBinding.inflate(container.inflater(), container, false)
    ){
        button.setOnClickListener { groupSetCallback(editText.text.toString()) }
        container.addView(root)
        return@with root
    }
}