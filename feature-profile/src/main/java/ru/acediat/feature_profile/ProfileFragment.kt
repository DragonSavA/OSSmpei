package ru.acediat.feature_profile

import android.annotation.SuppressLint
import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import ru.acediat.core_android.*
import ru.acediat.feature_profile.databinding.FragmentProfileBinding

class ProfileFragment : Fragment() {

    private lateinit var binding : FragmentProfileBinding

    private val viewModel : ProfileViewModel = ViewModelProvider
        .NewInstanceFactory()
        .create(ProfileViewModel::class.java)

    private lateinit var preferences: SharedPreferences

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        preferences = requireContext().getSharedPreferences(APP_PREFERENCES, Context.MODE_PRIVATE)

        initViewModel()

        binding = FragmentProfileBinding.inflate(layoutInflater, container, false)
        initViews()
        refresh()
        return binding.root
    }

    private fun initViewModel() = with(viewModel){
        setProfileObserver(viewLifecycleOwner, ::onAuthorize)
        setErrorObserver(viewLifecycleOwner, ::onError)
    }

    private fun initViews() = with(binding){
        profileRefreshLayout.setOnRefreshListener { refresh() }
    }

    private fun refresh() {
        viewModel.authorize(
            preferences.getInt(PROFILE_ID, 0),
            preferences.getString(PASSWORD, "") ?: ""
        )
        binding.profileRefreshLayout.isRefreshing = false
    }

    @SuppressLint("SetTextI18n")
    private fun onAuthorize(profile: Profile) = with(binding){
        profileName.text = profile.name + " " + profile.surname
        profileGroup.text = getString(R.string.group) + " " + profile.group
        profileScore.text = getString(R.string.balance) + " " + profile.capital
    }

    private fun onError(t: Throwable){
        Toast.makeText(requireContext(), t.message, Toast.LENGTH_LONG).show()
    }
}