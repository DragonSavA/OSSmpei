package ru.acediat.feature_profile.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.IdRes
import androidx.appcompat.content.res.AppCompatResources
import androidx.core.os.bundleOf
import androidx.core.view.isVisible
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.squareup.picasso.Picasso
import ru.acediat.core_android.BaseFragment
import ru.acediat.core_android.PHOTO_URL
import ru.acediat.feature_profile.R
import ru.acediat.core_res.R as resR
import ru.acediat.core_navigation.R as navR
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
        setTaskTakenObserver(viewLifecycleOwner, ::onTaskTaken)
        setErrorObserver(viewLifecycleOwner, ::onError)
    }

    override fun prepareViews() = with(binding){
        toolbar.backButton.setOnClickListener { requireActivity().onBackPressed() }
        takeButton.setOnClickListener { onTakeButtonClick() }
        createReportButton.setOnClickListener { startEditTaskReport() }
        giveUpButton.setOnClickListener { onGiveUpButtonClick() }
        redactReportButton.setOnClickListener { startEditTaskReport() }
        reportPhoto.setOnClickListener { onPhotoClick() }
        hideViews(viewModel.getTaskStatus())
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

    private fun onGiveUpButtonClick() = viewModel.refuseTask()

    private fun onPhotoClick() = findNavController().navigate(
        navR.id.photoViewFragment,
        bundleOf(PHOTO_URL to viewModel.getImageUrl()!!)
    )

    private fun startEditTaskReport() = findNavController().navigate(
        navR.id.editReportFragment,
        bundleOf(
            TASK_BUNDLE to viewModel.getTask(),
        )
    )

    //TODO: добавить сообщение о успешном взятии задания
    private fun onTaskTaken(b: Boolean){
        requireActivity().onBackPressed()
    }

    private fun onError(t: Throwable){}//TODO: добавить сообщение об ошибке

    private fun hideViews(status: String?) = with(binding){
        takeButton.isVisible = false
        when(status){
            ONGOING -> {
                reportLayout.isVisible = false
                redactReportButton.isVisible = false
                adminLayout.isVisible = false
            }
            IN_CHECKING -> {
                createReportButton.isVisible = false
                giveUpButton.isVisible = false
                adminLayout.isVisible = false
                bindReportLayout()
            }
            PAYED -> bindCheckedComplete(R.string.accepted, resR.drawable.shape_green_rectangle)
            CANCELED -> bindCheckedComplete(R.string.canceled, resR.drawable.shape_yellow_rectangle)
            PENALIZED -> bindCheckedComplete(R.string.penalized, resR.drawable.shape_red_rectangle)
            null -> {
                takeButton.isVisible = true
                reportLayout.isVisible = false
                redactReportButton.isVisible = false
                adminLayout.isVisible = false
                createReportButton.isVisible = false
                giveUpButton.isVisible = false
            }
        }
    }

    private fun bindCheckedComplete(@IdRes stringId: Int, @IdRes backgroundId: Int){
        whenCheckedComplete()
        bindReportLayout()
        bindResult(stringId, backgroundId)
    }

    private fun whenCheckedComplete() = with(binding){
        createReportButton.isVisible = false
        giveUpButton.isVisible = false
        redactReportButton.isVisible = false
        //TODO: заменить на строковый ресурс и добавить проверку на пустоту строки
        adminComment.text = viewModel.getTaskAdminComment() ?: "Остутствует"
    }

    private fun bindReportLayout(): Unit = with(binding){
        //TODO: заменить на строковый ресурс и добавить проверку на пустоту строки
        reportComment.text = viewModel.getTaskComment() ?: "Остутствует"
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