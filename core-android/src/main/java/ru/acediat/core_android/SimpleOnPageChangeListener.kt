package ru.acediat.core_android

import androidx.viewpager.widget.ViewPager

interface SimpleOnPageChangeListener : ViewPager.OnPageChangeListener {

    override fun onPageSelected(position: Int) {}

    override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {}

    override fun onPageScrollStateChanged(state: Int) {}

}