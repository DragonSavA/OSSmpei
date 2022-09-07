package ru.acediat.feature_timetable

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import ru.acediat.core_android.BaseFragment
import ru.acediat.core_android.SimpleOnPageChangeListener
import ru.acediat.core_utils.Time
import ru.acediat.feature_timetable.databinding.FragmentTimetableBinding
import ru.acediat.feature_timetable.di.TimetableComponent
import javax.inject.Inject

class TimetableFragment: BaseFragment<FragmentTimetableBinding, TimetableViewModel>(){

    @Inject lateinit var pagerAdapter: DaysAdapter

    override val viewModel : TimetableViewModel = ViewModelProvider
        .NewInstanceFactory()
        .create(TimetableViewModel::class.java)

    private val calendarLauncher = registerForActivityResult(CalendarContract()){
        it?.let {
            pagerAdapter.changeDays(it)
            refresh()
            binding.daysPager.currentItem = it.dayOfWeek.value - 1
            binding.dateText.text = viewModel.buildDateText(requireContext(), it)
        }
    }

    private val onPageChangedListener =  object : SimpleOnPageChangeListener{
        override fun onPageSelected(position: Int) {
            binding.dateText.text = viewModel.buildDateText(
                requireContext(),
                Time.getDateByWeekPosition(pagerAdapter.selectedDate, position)
            )
        }
    }

    override fun instanceBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentTimetableBinding = FragmentTimetableBinding.inflate(inflater, container, false)

    override fun inject() = with(TimetableComponent.init(requireContext())) {
        inject(this@TimetableFragment)
        inject(viewModel)
    }

    override fun prepareViewModel() = with(viewModel) {
        setTimetableObserver(viewLifecycleOwner, ::onTimetableReceived)
        setErrorObserver(viewLifecycleOwner, ::onError)
    }

    override fun prepareViews() = with(binding){
        pagerAdapter.setOnRefreshListener { refresh() }
        daysPager.adapter = pagerAdapter
        daysPager.currentItem = Time.currentDate().dayOfWeek.value - 1
        daysPager.addOnPageChangeListener(onPageChangedListener)
        daysTabs.setupWithViewPager(daysPager, true)
        dateText.text = viewModel.buildDateText(requireContext(), Time.currentDate())
        currentGroup.text = viewModel.getCurrentGroup()
        calendarButton.setOnClickListener{ startCalendar() }
    }

    override fun refresh(){
        viewModel.observeLessons(pagerAdapter.getFirstDate(), pagerAdapter.getLastDate())
    }

    private fun startCalendar() = calendarLauncher.launch(Time.currentDate())

    private fun onTimetableReceived(timetable : Timetable) = pagerAdapter.setData(timetable)

    private fun onError(t : Throwable) = //TODO: Сделать нормальное уведомление о ошибке
        Toast.makeText(requireContext(), t.message, Toast.LENGTH_SHORT).show()
}