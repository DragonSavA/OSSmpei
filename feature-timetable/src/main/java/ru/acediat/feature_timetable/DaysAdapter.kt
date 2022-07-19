package ru.acediat.feature_timetable

import android.view.View
import android.view.ViewGroup
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import androidx.viewpager.widget.PagerAdapter
import ru.acediat.core_android.Logger
import ru.acediat.core_android.OSS_TAG
import ru.acediat.core_res.loadingFrame
import ru.acediat.core_res.recyclerView
import java.time.LocalDateTime
import javax.inject.Inject

class DaysAdapter @Inject constructor(
    private val datePicker: DatePicker,
) : PagerAdapter() {

    private var timetable : Timetable? = null

    private var onRefresh: () -> Unit = {}

    var selectedDate : LocalDateTime = datePicker.getCurrentDate()
        private set

    fun setTimetable(timetable: Timetable){
        this.timetable = timetable
        notifyDataSetChanged()
    }

    fun changeDays(date : LocalDateTime){
        datePicker.setDates(date)
        selectedDate = date
        notifyDataSetChanged()
    }

    fun setOnRefreshListener(onRefresh: () -> Unit){
        this.onRefresh = onRefresh
    }

    fun getLastDate() = datePicker.getDate(count-1)

    fun getFirstDate() = datePicker.getDate(0)

    override fun getCount(): Int = datePicker.amountOfDays

    override fun instantiateItem(container: ViewGroup, position: Int) =
        if(timetable == null)
            instantiateLoading(container)
        else
            instantiateDayTimetable(container, position)

    override fun getPageTitle(position: Int): CharSequence = datePicker.getTimetableFormatDate(position)

    override fun isViewFromObject(view: View, `object`: Any): Boolean = view == `object`

    override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
        if(`object` !is Unit)
            container.removeView(`object` as View?)
    }

    override fun getItemPosition(`object`: Any): Int = POSITION_NONE

    private fun instantiateLoading(container : ViewGroup) : View = with(loadingFrame(container)) {
        container.addView(this)
        return this
    }

    private fun instantiateDayTimetable(container : ViewGroup, position: Int) : View =
        with(SwipeRefreshLayout(container.context)){
            layoutParams = ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT
            )
            addView(recyclerView(context).apply {
                adapter = LessonsAdapter().apply {
                    addItems(timetable!![position])
                    Logger.dArray(OSS_TAG, timetable!![position].toArray())
                }
            })
            setOnRefreshListener { onRefresh() }
            container.addView(this)
            return@with this
        }

    private fun instantiateEmpty(container : ViewGroup){}

}