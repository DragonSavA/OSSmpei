package ru.acediat.core_android

import android.view.View
import androidx.fragment.app.Fragment

abstract class PagerFragment : Fragment() {

    abstract fun getPages() : PairArray<String, View>

}