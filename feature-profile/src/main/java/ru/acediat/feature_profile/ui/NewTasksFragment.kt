package ru.acediat.feature_profile.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import ru.acediat.core_android.BaseFragment
import ru.acediat.feature_profile.databinding.FragmentNewTasksBinding
import ru.acediat.feature_profile.di.ProfileComponent
import ru.acediat.feature_profile.model.NewTasksViewModel
import ru.acediat.feature_profile.model.dtos.TaskDTO
import ru.acediat.feature_profile.ui.adapters.TasksAdapter
import ru.acediat.core_navigation.R as navR
import javax.inject.Inject

const val TASK_BUNDLE = "task_bundle"

class NewTasksFragment: BaseFragment<FragmentNewTasksBinding, NewTasksViewModel>() {

    @Inject lateinit var tasksAdapter: TasksAdapter

    override val viewModel: NewTasksViewModel = ViewModelProvider
        .NewInstanceFactory()
        .create(NewTasksViewModel::class.java)

    override fun inject() = with(ProfileComponent.init(requireContext())){
        inject(this@NewTasksFragment)
        inject(viewModel)
    }

    override fun instanceBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentNewTasksBinding = FragmentNewTasksBinding.inflate(inflater, container, false)

    override fun prepareViews() = with(binding){
        toolbar.backButton.setOnClickListener { requireActivity().onBackPressed() }
        tasksRecycler.adapter = tasksAdapter.apply { setOnTaskClickListener(::onTaskClick) }
    }

    override fun prepareViewModel() = with(viewModel){
        setTasksObserver(viewLifecycleOwner, ::onTasksReceived)
        setErrorObserver(viewLifecycleOwner, ::onError)
    }

    override fun refresh() {
        viewModel.getTasks()
    }

    private fun onTaskClick(task: TaskDTO) = findNavController().navigate(
        navR.id.taskFragment,
        bundleOf(TASK_BUNDLE to task)
    )

    private fun onTasksReceived(tasks: ArrayList<TaskDTO>) = tasksAdapter.setItems(tasks)

    private fun onError(t: Throwable) = Toast.makeText(requireContext(), "error", Toast.LENGTH_SHORT).show()

}