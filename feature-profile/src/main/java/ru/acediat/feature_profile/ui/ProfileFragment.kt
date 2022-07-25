package ru.acediat.feature_profile.ui

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.net.toUri
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.squareup.picasso.Picasso
import ru.acediat.feature_profile.model.Profile
import ru.acediat.feature_profile.model.ProfileViewModel
import ru.acediat.feature_profile.R
import ru.acediat.feature_profile.databinding.FragmentProfileBinding
import ru.acediat.feature_profile.di.ProfileComponent
import javax.inject.Inject

class ProfileFragment : Fragment() {

    @Inject lateinit var picasso: Picasso

    private lateinit var binding : FragmentProfileBinding

    private val viewModel : ProfileViewModel = ViewModelProvider
        .NewInstanceFactory()
        .create(ProfileViewModel::class.java)

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentProfileBinding.inflate(inflater, container, false)
        inject()
        initViewModel()
        initViews()
        refresh()
        return binding.root
    }

    private fun inject() = with(ProfileComponent.init(requireContext())){
        inject(this@ProfileFragment)
        inject(viewModel)
    }

    private fun initViewModel() = with(viewModel){
        setProfileObserver(viewLifecycleOwner, ::onAuthorize)
        setErrorObserver(viewLifecycleOwner, ::onError)
    }

    private fun initViews() = with(binding){
        shopButton.setOnClickListener(::onShopClick)
        profileRefreshLayout.setOnRefreshListener { refresh() }
    }

    private fun refresh() {
        viewModel.authorize()
        binding.profileRefreshLayout.isRefreshing = false
    }

    private fun onShopClick(view: View){
        findNavController().navigate("https://mpei.oss/shop".toUri())
    }

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