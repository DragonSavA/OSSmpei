package ru.acediat.feature_profile.ui

import android.Manifest
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.lifecycle.ViewModelProvider
import com.squareup.picasso.Picasso
import ru.acediat.core_android.BaseFragment
import ru.acediat.core_android.FileUtil
import ru.acediat.core_android.Logger
import ru.acediat.core_android.OSS_TAG
import ru.acediat.core_android.ext.checkPermission
import ru.acediat.core_android.ext.requestPermission
import ru.acediat.feature_profile.databinding.FragmentEditReportBinding
import ru.acediat.feature_profile.di.ProfileComponent
import ru.acediat.feature_profile.model.EditReportViewModel
import ru.acediat.feature_profile.model.dtos.TaskDTO
import javax.inject.Inject

private const val CAMERA_CODE = 0
private const val CHOOSE_PHOTO_CODE = 1

class EditReportFragment: BaseFragment<FragmentEditReportBinding, EditReportViewModel>() {

    @Inject lateinit var picasso: Picasso

    override val viewModel: EditReportViewModel = ViewModelProvider
        .NewInstanceFactory()
        .create(EditReportViewModel::class.java)

    private val cameraLauncher = registerForActivityResult(CameraContract()){
        if(it == -1) onPhotoTaken()
    }

    private val choosePhotoLauncher = registerForActivityResult(ChoosePhotoContract()){
        it?.let{
            viewModel.setUri(it)
            onPhotoTaken()
        }
    }

    override fun instanceBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentEditReportBinding =
        FragmentEditReportBinding.inflate(inflater, container, false)

    override fun inject() = with(ProfileComponent.init(requireContext())){
        inject(this@EditReportFragment)
        inject(viewModel)
    }

    override fun prepareViewModel() = with(viewModel){
        task = arguments?.get(TASK_BUNDLE) as TaskDTO
        setOnErrorObserver(viewLifecycleOwner, ::onError)
        setOnCompleteSendReport { }
    }

    override fun prepareViews(): Unit = with(binding){
        addPhoto.setOnClickListener { launchSelection() }
        createPhoto.setOnClickListener { launchCamera() }
        deleteImage.setOnClickListener { deleteImage() }
        backButton.setOnClickListener { requireActivity().onBackPressed() }
        sendReport.setOnClickListener { onSendReportClick() }

        taskName.text = viewModel.task?.shortDescription
        comment.setText(viewModel.task?.comment)
        viewModel.task?.imageUrl?.let{
            setAddPhotoViewsVisible(false)
            loadReportImage(it)
        } ?: run{
            setImageViewsVisible(false)
        }
    }

    override fun refresh() { }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) = when(requestCode){
        CAMERA_CODE -> launchCamera()
        CHOOSE_PHOTO_CODE -> launchSelection()
        else -> {}
    }

    private fun onSendReportClick() = viewModel.sendReport(
        binding.comment.text.toString(),
        FileUtil.from(requireContext(), viewModel.getUri()!!)
    )

    private fun onPhotoTaken(){
        setAddPhotoViewsVisible(false)
        setImageViewsVisible(true)
        viewModel.getUri()?.let{ binding.reportImage.setImageURI(it) }
    }

    private fun deleteImage(){
        setAddPhotoViewsVisible(true)
        setImageViewsVisible(false)
        viewModel.deleteUri()
    }

    private fun setAddPhotoViewsVisible(isVisible: Boolean) = with(binding){
        createPhoto.isVisible = isVisible
        addPhoto.isVisible = isVisible
    }

    private fun setImageViewsVisible(isVisible: Boolean) = with(binding){
        reportImage.isVisible = isVisible
        deleteImage.isVisible = isVisible
    }

    private fun loadReportImage(path: String) = picasso.load(path)
        .fit()
        .centerCrop()
        .into(binding.reportImage)

    private fun launchCamera(){
        if (checkPermission(Manifest.permission.CAMERA))
            requestPermission(Manifest.permission.CAMERA, CAMERA_CODE)
        cameraLauncher.launch(viewModel.createImageFile(requireContext()))
    }

    private fun launchSelection(){
        if(checkPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE))
            requestPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE, CHOOSE_PHOTO_CODE)
        choosePhotoLauncher.launch(0)
    }

    private fun onError(t: Throwable){
        Logger.e(OSS_TAG, "ERROR", t)
    }
}