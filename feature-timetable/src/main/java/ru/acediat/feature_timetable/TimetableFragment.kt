package ru.acediat.feature_timetable

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import ru.acediat.feature_timetable.databinding.FragmentTimetableBinding
import ru.acediat.feature_timetable.di.TimetableComponent
import javax.inject.Inject

class TimetableFragment : Fragment() {

    private val viewModel : TimetableViewModel = ViewModelProvider
        .NewInstanceFactory()
        .create(TimetableViewModel::class.java)

    private lateinit var binding : FragmentTimetableBinding

    @Inject lateinit var pagerAdapter: DaysAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentTimetableBinding.inflate(inflater, container, false)

        TimetableComponent.init().inject(this)

        with(binding){
            daysPager.adapter = pagerAdapter
            daysTabs.setupWithViewPager(daysPager)
        }

        return binding.root
    }

    companion object {

        @JvmStatic
        fun newInstance() = TimetableFragment()

    }
}