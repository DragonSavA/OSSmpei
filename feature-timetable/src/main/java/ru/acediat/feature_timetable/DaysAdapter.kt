package ru.acediat.feature_timetable

import android.view.View
import android.view.ViewGroup
import androidx.viewpager.widget.PagerAdapter
import ru.acediat.core_android.Logger
import ru.acediat.core_android.OSS_TAG
import ru.acediat.core_res.loadingFrame
import java.time.LocalDateTime
import javax.inject.Inject

class DaysAdapter @Inject constructor(private val datePicker: DatePicker) : PagerAdapter() {

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
        if(timetable == null)
            with(loadingFrame(container)) {
                container.addView(this)
                return this
            }
        return container
    }

    override fun getPageTitle(position: Int): CharSequence = datePicker.getFormatDate(position)

    override fun isViewFromObject(view: View, `object`: Any): Boolean = view == `object`

    override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) =
        container.removeView(`object` as View)

    override fun getItemPosition(`object`: Any): Int = POSITION_NONE

}