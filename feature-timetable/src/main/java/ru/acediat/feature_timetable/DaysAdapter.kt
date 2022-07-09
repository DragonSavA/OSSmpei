package ru.acediat.feature_timetable

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.viewpager.widget.PagerAdapter
import ru.acediat.core_res.loadingFrame
import javax.inject.Inject

class DaysAdapter @Inject constructor(private val datePicker: DatePicker) : PagerAdapter() {

    private var timetable : Timetable? = null

    fun setTimetable(timetable: Timetable){
        this.timetable = timetable
        notifyDataSetChanged()
    }

    override fun getCount(): Int = datePicker.amountOfDays

    override fun instantiateItem(container: ViewGroup, position: Int): Any {
        if(timetable == null)
            with(loadingFrame(LayoutInflater.from(container.context), container)) {
                container.addView(this)
                return this
            }

        return container
    }

    override fun getPageTitle(position: Int): CharSequence = datePicker.getFormatDate(position)

    override fun isViewFromObject(view: View, `object`: Any): Boolean = view == `object`

    override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) =
        container.removeView(`object` as View)
}