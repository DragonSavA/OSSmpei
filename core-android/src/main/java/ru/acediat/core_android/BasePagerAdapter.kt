package ru.acediat.core_android

import android.view.View
import android.view.ViewGroup
import androidx.viewpager.widget.PagerAdapter


//TODO: базовый класс фигня, надо придумать что-то более универсальное
abstract class BasePagerAdapter<T> : PagerAdapter(){

    protected var data : T? = null

    @JvmName("setData1")
    fun setData(data : T){
        this.data = data
        notifyDataSetChanged()
    }

    override fun isViewFromObject(view: View, `object`: Any): Boolean = view == `object`

    override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
        if(`object` !is Unit)
            container.removeView(`object` as View?)
    }

    override fun getItemPosition(`object`: Any): Int = POSITION_NONE

    protected abstract fun instantiateLoading(container: ViewGroup) : View
}