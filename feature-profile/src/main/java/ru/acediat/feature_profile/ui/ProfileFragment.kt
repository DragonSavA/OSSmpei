package ru.acediat.feature_profile.ui

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.squareup.picasso.Picasso
import ru.acediat.core_android.BaseFragment
import ru.acediat.feature_profile.model.Profile
import ru.acediat.feature_profile.model.ProfileViewModel
import ru.acediat.feature_profile.R
import ru.acediat.core_navigation.R as navR
import ru.acediat.feature_profile.databinding.FragmentProfileBinding
import ru.acediat.feature_profile.di.ProfileComponent
import javax.inject.Inject

const val FULL_NAME_BUNDLE = "fullName"
const val GROUP_BUNDLE = "group"
const val BALANCE_BUNDLE = "balance"
const val IMAGE_URL_BUNDLE = "imageUrl"

class ProfileFragment : BaseFragment<FragmentProfileBinding, ProfileViewModel>() {

    @Inject lateinit var picasso: Picasso

    override val viewModel : ProfileViewModel = ViewModelProvider
        .NewInstanceFactory()
        .create(ProfileViewModel::class.java)

    override fun instanceBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentProfileBinding
        = FragmentProfileBinding.inflate(inflater, container, false)

    override fun inject() = with(ProfileComponent.init(requireContext())){
        inject(this@ProfileFragment)
        inject(viewModel)
    }

    override fun initViewModel() = with(viewModel){
        setProfileObserver(viewLifecycleOwner, ::onAuthorize)
        setErrorObserver(viewLifecycleOwner, ::onError)
    }

    override fun bindViews() = with(binding){
        shopButton.setOnClickListener(::onShopClick)
        profileRefreshLayout.setOnRefreshListener { refresh() }
    }

    override fun refresh() {
        viewModel.authorize()
        binding.profileRefreshLayout.isRefreshing = false
    }

    override fun saveState(outState: Bundle) = with(outState){
        putString(FULL_NAME_BUNDLE, binding.profileName.text.toString())
        putString(GROUP_BUNDLE, binding.profileGroup.text.toString())
        putString(BALANCE_BUNDLE, binding.profileScore.text.toString())
        putString(IMAGE_URL_BUNDLE, viewModel.imageUrl)
    }

    override fun loadState(savedInstanceState: Bundle) = with(binding){
        viewModel.restoreData(savedInstanceState)
        profileName.text = savedInstanceState.getString(FULL_NAME_BUNDLE)
        profileGroup.text = savedInstanceState.getString(GROUP_BUNDLE)
        profileScore.text = savedInstanceState.getString(BALANCE_BUNDLE)
        picasso.load(savedInstanceState.getString(IMAGE_URL_BUNDLE))
            .error(ru.acediat.core_res.R.drawable.ic_load) //TODO: заменить на что-то норм
            .fit()
            .centerCrop()
            .into(profileAvatar)
    }

    private fun onShopClick(view: View) = findNavController().navigate(navR.id.shop)

    @SuppressLint("SetTextI18n")
    private fun onAuthorize(profile: Profile) = with(binding){
        profileName.text = profile.name + " " + profile.surname
        profileGroup.text = getString(R.string.group) + " " + profile.group
        profileScore.text = getString(R.string.balance) + " " + profile.capital
        picasso.load(profile.imageSrc)
            .fit()
            .centerCrop()
            .into(profileAvatar)
    }

    private fun onError(t: Throwable) =
        Toast.makeText(requireContext(), t.message, Toast.LENGTH_LONG).show()

}