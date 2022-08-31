package ru.acediat.feature_profile.ui

import android.Manifest
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.core.view.isVisible
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.squareup.picasso.Picasso
import ru.acediat.core_android.*
import ru.acediat.core_android.ext.checkPermission
import ru.acediat.core_android.ext.requestPermission
import ru.acediat.core_navigation.R
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
        setOnCompleteReportSendObserver(viewLifecycleOwner){ requireActivity().onBackPressed() }
        setOnErrorObserver(viewLifecycleOwner, ::onError)
    }

    override fun prepareViews(): Unit = with(binding){
        addPhoto.setOnClickListener { launchPhotoSelection() }
        createPhoto.setOnClickListener { launchCamera() }
        deleteImage.setOnClickListener { deleteImage() }
        backButton.setOnClickListener { requireActivity().onBackPressed() }
        sendReport.setOnClickListener { onSendReportClick() }
        reportImage.setOnClickListener { onPhotoClick() }
        taskName.text = viewModel.task?.shortDescription
        comment.setText(viewModel.task?.comment)
        setImageViewsVisible(false)
    }

    override fun refresh() { }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) = when(requestCode){
        CAMERA_CODE -> launchCamera()
        CHOOSE_PHOTO_CODE -> launchPhotoSelection()
        else -> {}
    }

    private fun onPhotoClick() = findNavController().navigate(
        R.id.photoViewFragment,
        bundleOf(PHOTO_URL to viewModel.task?.getImageUrl()!!)
    )

    private fun onSendReportClick() = viewModel.getUri()?.let{
        viewModel.sendReport(
            binding.comment.text.toString(),
            FileUtil.from(requireContext(), it)
        )
    } ?: run{
        //TODO: сделать уведомление о том, что картинку нужно установить вообще-то
    }

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

    private fun launchCamera(){
        if(!checkPermission(Manifest.permission.CAMERA))
            requestPermission(Manifest.permission.CAMERA, CAMERA_CODE)
        cameraLauncher.launch(viewModel.createImageFile(requireContext()))
    }

    private fun launchPhotoSelection(){
        if(!checkPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE))
            requestPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE, CHOOSE_PHOTO_CODE)
        choosePhotoLauncher.launch(0)
    }

    private fun onError(t: Throwable){//TODO: уведомление об ошибке
        Logger.e(OSS_TAG, "ERROR", t)
    }
}