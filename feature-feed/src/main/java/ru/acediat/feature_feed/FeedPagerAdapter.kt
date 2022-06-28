package ru.acediat.feature_feed

import android.view.View
import ru.acediat.core_android.BasePagerAdapter
import ru.acediat.core_android.PairArray

class FeedPagerAdapter(
    pages : PairArray<String, View>
) : BasePagerAdapter(pages)