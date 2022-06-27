package ru.acediat.core_android

import android.view.View
import android.view.ViewGroup
import androidx.viewpager.widget.PagerAdapter

abstract class BasePagerAdapter(
    private val pages : PairArray<String, View>
) : PagerAdapter(){

    override fun getCount(): Int = pages.size

    override fun getPageTitle(position: Int): CharSequence = pages[position].first

    override fun instantiateItem(container: ViewGroup, position: Int): Any =
        with(pages[position].second){
            container.addView(this)
            return this
        }

    override fun isViewFromObject(view: View, `object`: Any): Boolean = view == `object`

    override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
        container.removeView(`object` as View)
    }

}