package ru.acediat.feature_profile.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.IdRes
import androidx.appcompat.content.res.AppCompatResources
import androidx.core.view.isVisible
import androidx.lifecycle.ViewModelProvider
import com.squareup.picasso.Picasso
import ru.acediat.core_android.BaseFragment
import ru.acediat.feature_profile.R
import ru.acediat.core_res.R as resR
import ru.acediat.feature_profile.databinding.FragmentTaskBinding
import ru.acediat.feature_profile.di.ProfileComponent
import ru.acediat.feature_profile.model.TaskViewModel
import ru.acediat.feature_profile.model.dtos.*
import javax.inject.Inject

class TaskFragment: BaseFragment<FragmentTaskBinding, TaskViewModel>() {

    @Inject lateinit var picasso: Picasso

    override val viewModel: TaskViewModel = ViewModelProvider
        .NewInstanceFactory()
        .create(TaskViewModel::class.java)

    override fun instanceBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentTaskBinding = FragmentTaskBinding.inflate(inflater, container, false)

    override fun inject() = with(ProfileComponent.init(requireContext())){
        inject(this@TaskFragment)
        inject(viewModel)
    }

    override fun prepareViewModel() = with(viewModel){
        setTask(arguments?.get(TASK_BUNDLE) as TaskDTO)
        setOnTaskTakenCallback {  }//TODO: добавить сообщение о успешном взятии задания
        setErrorObserver(viewLifecycleOwner, ::onError)
    }

    override fun prepareViews() = with(binding){
        toolbar.backButton.setOnClickListener { requireActivity().onBackPressed() }
        takeButton.setOnClickListener{ onTakeButtonClick() }
        viewModel.getTaskStatus()?.let { hideViews(it) }
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

    private fun hideViews(status: String) = with(binding){
        takeButton.isVisible = false
        when(status){
            ONGOING -> {
                reportLayout.isVisible = false
                redactReportButton.isVisible = false
                adminLayout.isVisible = false
            }
            IN_CHECKING -> {
                sendButton.isVisible = false
                giveUpButton.isVisible = false
                adminLayout.isVisible = false
                bindReportLayout()
            }
            PAYED -> bindCheckedComplete(R.string.accepted, resR.drawable.shape_green_rectangle)
            CANCELED -> bindCheckedComplete(R.string.canceled, resR.drawable.shape_yellow_rectangle)
            PENALIZED -> bindCheckedComplete(R.string.penalized, resR.drawable.shape_red_rectangle)
        }
    }

    private fun bindCheckedComplete(@IdRes stringId: Int, @IdRes backgroundId: Int){
        whenCheckedComplete()
        bindReportLayout()
        bindResult(stringId, backgroundId)
    }

    private fun whenCheckedComplete() = with(binding){
        sendButton.isVisible = false
        giveUpButton.isVisible = false
        redactReportButton.isVisible = false
        adminComment.text = viewModel.getTaskAdminComment() ?: "Остутствует"//TODO: заменить на строковый ресурс
    }

    private fun bindReportLayout(): Unit = with(binding){
        reportComment.text = viewModel.getTaskComment() ?: "Остутствует"//TODO: заменить на строковый ресурс
        viewModel.getImageUrl()?.let{
            picasso.load(it)
                .fit()
                .centerCrop()
                .into(reportPhoto)
        } ?: run{
            reportPhoto.isVisible = false
        }
    }

    private fun bindResult(@IdRes stringId: Int, @IdRes backgroundId: Int){
        binding.result.text = getString(stringId)
        binding.result.background = AppCompatResources.getDrawable(
            requireContext(), backgroundId
        )
    }
}