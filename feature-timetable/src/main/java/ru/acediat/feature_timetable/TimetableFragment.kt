package ru.acediat.feature_timetable

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.PopupMenu
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import ru.acediat.core_android.BaseFragment
import ru.acediat.core_android.Logger
import ru.acediat.core_android.OSS_TAG
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

    private lateinit var groupsMenu: PopupMenu

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
        setOnGroupValidObserver(viewLifecycleOwner, ::onGroupValidReceived)
        setCurrentGroupObserver(viewLifecycleOwner, ::onGroupReceived)
        setFavoriteGroupsObserver(viewLifecycleOwner, ::onFavoriteGroupsReceived)
        setErrorObserver(viewLifecycleOwner, ::onError)
    }

    override fun prepareViews() = with(binding){
        daysPager.adapter = pagerAdapter.apply{
            setOnRefreshListener { refresh() }
            setGroupSetCallback { onSetGroupClick(it) }
        }
        daysPager.currentItem = Time.currentDate().dayOfWeek.value - 1
        daysPager.addOnPageChangeListener(onPageChangedListener)
        daysTabs.setupWithViewPager(daysPager, true)
        dateText.text = viewModel.buildDateText(requireContext(), Time.currentDate())
        calendarButton.setOnClickListener{ startCalendar() }
        groupsMenu = PopupMenu(requireContext(), binding.currentGroup).apply {
            setOnMenuItemClickListener {
                viewModel.isGroupValid(it.title.toString())
                true
            }
        }
        currentGroup.setOnClickListener { groupsMenu.show() }
    }

    override fun refresh(): Unit = with(viewModel){
        getCurrentGroup()
        if(viewModel.isAuth())
            getFavoriteGroups()
    }

    private fun onSetGroupClick(group: String){
        viewModel.isGroupValid(group)
    }

    private fun onGroupReceived(group: String){
        binding.currentGroup.text = group
        if(group != "" && group != "Другое") {
            pagerAdapter.isEmptyGroup = false
            viewModel.observeLessons(pagerAdapter.getFirstDate(), pagerAdapter.getLastDate())
        }else {
            pagerAdapter.isEmptyGroup = true
            pagerAdapter.notifyDataSetChanged()
        }
    }

    private fun onGroupValidReceived(isValid: Pair<String, Boolean>) = if(isValid.second){
        viewModel.setCurrentGroup(isValid.first)
        pagerAdapter.isEmptyGroup = false
        pagerAdapter.notifyDataSetChanged()
        refresh()
    }else{
        showErrorSnackBar(binding.root, getString(R.string.error_group))
    }

    private fun onFavoriteGroupsReceived(groups: ArrayList<String>){
        groupsMenu.menu.clear()
        for(i in groups.indices){
            groupsMenu.menu.add(0, groups[i].hashCode(), i, groups[i])
        }
    }

    private fun startCalendar() = calendarLauncher.launch(Time.currentDate())

    private fun onTimetableReceived(timetable : Timetable) = pagerAdapter.setData(timetable)

    private fun onError(t : Throwable) = //TODO: Сделать нормальное уведомление о ошибке
        Toast.makeText(requireContext(), t.message, Toast.LENGTH_SHORT).show()
}