package ru.acediat.feature_profile

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.squareup.picasso.Picasso
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
        binding = FragmentProfileBinding.inflate(layoutInflater, container, false)
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
        profileRefreshLayout.setOnRefreshListener { refresh() }
    }

    private fun refresh() {
        viewModel.authorize()
        binding.profileRefreshLayout.isRefreshing = false
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

    private fun onError(t: Throwable){
        Toast.makeText(requireContext(), t.message, Toast.LENGTH_LONG).show()
    }
}