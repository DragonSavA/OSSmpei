package ru.acediat.feature_profile.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.lifecycle.ViewModelProvider
import ru.acediat.core_android.BaseFragment
import ru.acediat.core_android.Logger
import ru.acediat.core_android.OSS_TAG
import ru.acediat.feature_profile.apis.ACCEPTED
import ru.acediat.feature_profile.databinding.FragmentTaskBinding
import ru.acediat.feature_profile.di.ProfileComponent
import ru.acediat.feature_profile.model.TaskViewModel
import ru.acediat.feature_profile.model.dtos.*

class TaskFragment: BaseFragment<FragmentTaskBinding, TaskViewModel>() {

    override val viewModel: TaskViewModel = ViewModelProvider
        .NewInstanceFactory()
        .create(TaskViewModel::class.java)

    override fun instanceBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentTaskBinding = FragmentTaskBinding.inflate(inflater, container, false)

    override fun inject() = with(ProfileComponent.init(requireContext())){
        inject(viewModel)
    }

    override fun prepareViewModel() = with(viewModel){
        setTask(arguments?.get(TASK_BUNDLE) as TaskDTO)
        setOnTaskTakenCallback {  }//TODO: добавить сообщение о успешном взятии задания
        setErrorObserver(viewLifecycleOwner, ::onError)
    }

    override fun prepareViews() = with(binding){
        toolbar.backButton.setOnClickListener { requireActivity().onBackPressed() }
        val taskStatus = viewModel.getTaskStatus()
        if(taskStatus != null){
            takeButton.isVisible = false
            when(taskStatus){
                ONGOING -> {
                    reportLayout.isVisible = false
                    redactReportButton.isVisible = false
                    adminLayout.isVisible = false
                }
                IN_CHECKING -> {
                    sendButton.isVisible = false
                    giveUpButton.isVisible = false
                    adminLayout.isVisible = false
                }
                PAYED -> {
                   whenCheckedComplete()
                }
                CANCELED -> {
                    whenCheckedComplete()
                }
                PENALIZED -> {
                    whenCheckedComplete()
                }
            }
        }else{
            takeButton.setOnClickListener{ onTakeButtonClick() }
        }
        with(viewModel){
            shortDescription.text = getTaskShortDescription()
            fullDescription.text = getTaskDescription()
            reward.text = getTaskReward()
            deadlines.text = getTaskDeadlines()
            refuseBefore.text = getTaskRefuseBeforeDate()
            penalty.text = getTaskPenalty()
            place.text = getTaskPlace()
        }
    }

    override fun refresh() {}

    private fun onTakeButtonClick() = viewModel.takeTask()

    private fun onError(t: Throwable){}//TODO: добавить сообщение об ошибке

    private fun whenCheckedComplete() = with(binding){
        sendButton.isVisible = false
        giveUpButton.isVisible = false
        redactReportButton.isVisible = false
        reportComment.text = viewModel.getTaskComment() ?: "Остутствует"//TODO: заменить на строковый ресурс
    }
}