package ru.acediat.feature_profile.ui

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.squareup.picasso.Picasso
import ru.acediat.core_android.BaseFragment
import ru.acediat.core_android.PHOTO_URL
import ru.acediat.feature_profile.model.Profile
import ru.acediat.feature_profile.model.ProfileViewModel
import ru.acediat.feature_profile.R
import ru.acediat.core_navigation.R as navR
import ru.acediat.feature_profile.databinding.FragmentProfileBinding
import ru.acediat.feature_profile.di.ProfileComponent
import javax.inject.Inject

const val PROFILE_BUNDLE = "profile"

class ProfileFragment : BaseFragment<FragmentProfileBinding, ProfileViewModel>() {

    @Inject lateinit var picasso: Picasso

    override val viewModel : ProfileViewModel = ViewModelProvider
        .NewInstanceFactory()
        .create(ProfileViewModel::class.java)

    override fun instanceBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentProfileBinding = FragmentProfileBinding.inflate(inflater, container, false)

    override fun inject() = with(ProfileComponent.init(requireContext())){
        inject(this@ProfileFragment)
        inject(viewModel)
    }

    override fun prepareViewModel() = with(viewModel){
        setProfileObserver(viewLifecycleOwner, ::onAuthorize)
        setErrorObserver(viewLifecycleOwner, ::onError)
    }

    override fun prepareViews() = with(binding){
        shopButton.setOnClickListener { onShopClick() }
        takenTasksButton.setOnClickListener { onTakenTasks() }
        newTasksButton.setOnClickListener { onNewTasksClick() }
        profileRefreshLayout.setOnRefreshListener { refresh() }
    }

    override fun refresh() {
        viewModel.authorize()
        binding.profileRefreshLayout.isRefreshing = false
    }

    private fun onPhotoClick(imageUrl: String) = findNavController().navigate(
        navR.id.photoViewFragment,
        bundleOf(PHOTO_URL to imageUrl)
    )

    private fun onShopClick() = findNavController().navigate(navR.id.shop)

    private fun onTakenTasks() = findNavController().navigate(navR.id.takenTasks)

    private fun onNewTasksClick() = findNavController().navigate(navR.id.newTasks)

    private fun onInfoClick(imageUrl: String) = findNavController().navigate(
        navR.id.settingsFragment,
        bundleOf(IMAGE_URL_BUNDLE to imageUrl)
    )

    @SuppressLint("SetTextI18n")
    private fun onAuthorize(profile: Profile) = with(binding){
        profileName.text = profile.name + " " + profile.surname
        profileGroup.text = getString(R.string.group) + " " + profile.group
        profileScore.text = getString(R.string.balance) + " " + profile.capital
        profileAvatar.setOnClickListener { onPhotoClick(profile.imageSrc) }
        profileInfo.setOnClickListener { onInfoClick(profile.imageSrc) }
        picasso.load(profile.imageSrc)
            .fit()
            .centerCrop()
            .into(profileAvatar)
    }

    private fun onError(t: Throwable) =
        Toast.makeText(requireContext(), t.message, Toast.LENGTH_LONG).show()

}