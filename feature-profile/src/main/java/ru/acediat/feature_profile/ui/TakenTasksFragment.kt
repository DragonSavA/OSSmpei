package ru.acediat.feature_profile.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import ru.acediat.core_android.BaseFragment
import ru.acediat.core_android.Logger
import ru.acediat.core_android.OSS_TAG
import ru.acediat.feature_profile.databinding.FragmentTakenTasksBinding
import ru.acediat.feature_profile.di.ProfileComponent
import ru.acediat.feature_profile.model.TakenTasksViewModel
import ru.acediat.feature_profile.model.dtos.TaskDTO
import ru.acediat.feature_profile.ui.adapters.TasksSectionsAdapter
import ru.acediat.core_navigation.R as navR
import javax.inject.Inject

class TakenTasksFragment: BaseFragment<FragmentTakenTasksBinding, TakenTasksViewModel>() {

    @Inject lateinit var sectionsAdapter: TasksSectionsAdapter

    override val viewModel: TakenTasksViewModel = ViewModelProvider
        .NewInstanceFactory()
        .create(TakenTasksViewModel::class.java)

    override fun instanceBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentTakenTasksBinding =
        FragmentTakenTasksBinding.inflate(inflater, container, false)

    override fun inject() = with(ProfileComponent.init(requireContext())) {
        inject(viewModel)
        inject(this@TakenTasksFragment)
    }

    override fun prepareViewModel() = with(viewModel){
        setTakenTasksObserver(viewLifecycleOwner, ::onTakenTasksReceived)
        setInCheckTasksObserver(viewLifecycleOwner, ::onInCheckTasksReceived)
        setAcceptedTasksObserver(viewLifecycleOwner, ::onAcceptedTasksReceived)
        setRefusedTasksObserver(viewLifecycleOwner, ::onRefusedTasksReceived)
        setErrorObserver(viewLifecycleOwner, ::onError)
    }

    override fun prepareViews(): Unit = with(binding){
        sectionsPager.adapter = sectionsAdapter.apply{
            setOnTaskClickListener {
                findNavController().navigate(
                    navR.id.taskFragment,
                    bundleOf(TASK_BUNDLE to it)
                )
            }
            setNavController(findNavController())
            setOnRefresh { refresh() }
        }
        sectionTabs.setupWithViewPager(sectionsPager)
        toolbar.backButton.setOnClickListener{ requireActivity().onBackPressed() }
    }

    override fun refresh() = viewModel.getAllTasks()

    private fun onTakenTasksReceived(list: ArrayList<TaskDTO>) =
        sectionsAdapter.setTakenTasks(list)

    private fun onInCheckTasksReceived(list: ArrayList<TaskDTO>) =
        sectionsAdapter.setInCheckTasks(list)

    private fun onAcceptedTasksReceived(list: ArrayList<TaskDTO>) =
        sectionsAdapter.setAcceptedTasks(list)

    private fun onRefusedTasksReceived(list: ArrayList<TaskDTO>) =
        sectionsAdapter.setRefusedTasks(list)

    //TODO: оибработка ошибок
    private fun onError(t: Throwable) = Logger.e(OSS_TAG, "ERROR", t)
}