package ru.acediat.feature_timetable

import android.content.Context
import android.view.View
import android.view.ViewGroup
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import androidx.viewpager.widget.PagerAdapter
import ru.acediat.core_res.loadingFrame
import ru.acediat.core_res.recyclerView
import java.time.LocalDateTime
import javax.inject.Inject

class DaysAdapter @Inject constructor(
    private val datePicker: DatePicker,
) : PagerAdapter() {

    private var timetable : Timetable? = null

    fun setTimetable(timetable: Timetable){
        this.timetable = timetable
        notifyDataSetChanged()
    }

    fun changeDays(date : LocalDateTime) {
        datePicker.setDates(date)
        notifyDataSetChanged()
    }

    override fun getCount(): Int = datePicker.amountOfDays

    override fun instantiateItem(container: ViewGroup, position: Int): Any {
        return if(timetable == null)
            instantiateLoading(container)
        else
            instantiateDayTimetable(container.context, position)
    }

    override fun getPageTitle(position: Int): CharSequence = datePicker.getFormatDate(position)

    override fun isViewFromObject(view: View, `object`: Any): Boolean = view == `object`

    override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) =
        container.removeView(`object` as View)

    override fun getItemPosition(`object`: Any): Int = POSITION_NONE

    private fun instantiateLoading(container : ViewGroup) : Any = with(loadingFrame(container)) {
        container.addView(this)
        return this
    }

    private fun instantiateDayTimetable(context : Context, position: Int) : Any {
        val refreshLayout = SwipeRefreshLayout(context)
        refreshLayout.addView(recyclerView(context).apply{
            adapter = EventsAdapter(ViewHoldersManagerImpl()).apply {
                currentList.addAll(timetable!![position])
            }
        })
        return refreshLayout
    }

    private fun instantiateEmpty(container : ViewGroup){}

}