package ru.acediat.feature_feed.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import ru.acediat.core_android.PagerFragment
import ru.acediat.core_android.PairArray
import ru.acediat.feature_feed.databinding.FragmentFeedBinding

class FeedFragment : PagerFragment() {

    private lateinit var binding : FragmentFeedBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentFeedBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun getPages(): PairArray<String, View> = arrayOf()

    companion object {
        @JvmStatic
        fun newInstance() = FeedFragment()
    }
}