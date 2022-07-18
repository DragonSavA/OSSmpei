package ru.acediat.feature_timetable

import android.content.Context
import android.view.View
import android.view.ViewGroup
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import androidx.viewpager.widget.PagerAdapter
import ru.acediat.core_android.AdapterCallback
import ru.acediat.core_android.Logger
import ru.acediat.core_android.OSS_TAG
import ru.acediat.core_android.RecyclerViewAdapter
import ru.acediat.core_res.loadingFrame
import ru.acediat.core_res.recyclerView
import ru.acediat.feature_timetable.entities.Lesson
import java.time.LocalDateTime
import javax.inject.Inject

class DaysAdapter @Inject constructor(
    private val datePicker: DatePicker,
) : PagerAdapter() {

    private var timetable : Timetable? = null

    fun setTimetable(timetable: Timetable){
        Logger.d(OSS_TAG, "timetable setted")
        this.timetable = timetable
        notifyDataSetChanged()
    }

    fun changeDays(date : LocalDateTime) {
        datePicker.setDates(date)
        notifyDataSetChanged()
    }

    override fun getCount(): Int = datePicker.amountOfDays

    override fun instantiateItem(container: ViewGroup, position: Int): Any {
        Logger.d(OSS_TAG, "item instantiated")
        return if(timetable == null)
            instantiateLoading(container)
        else
            instantiateDayTimetable(container, position)
    }

    override fun getPageTitle(position: Int): CharSequence = datePicker.getFormatDate(position)

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
            Logger.d(OSS_TAG, "inst timetable")
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
            setOnRefreshListener {
                //TODO: тут явно нужно переделать откуда берется SwipeRefreshLayout, потому что сейчас обновляться неоткуда
            }
            container.addView(this)
            return@with this
        }

    private fun instantiateEmpty(container : ViewGroup){}

}