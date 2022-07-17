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
import androidx.appcompat.content.res.AppCompatResources
import androidx.core.view.isVisible
import androidx.lifecycle.ViewModelProvider
import ru.acediat.core_android.*
import ru.acediat.core_res.loadingFrame
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

        viewModel.setProfileObserver(viewLifecycleOwner, ::onAuthorize)
        viewModel.setErrorObserver(viewLifecycleOwner, ::onError)

        binding = FragmentProfileBinding.inflate(layoutInflater, container, false)
        with(binding){
            profileRefreshLayout.setOnRefreshListener {
                refresh()
                profileRefreshLayout.isRefreshing = false
            }
        }

        refresh()
        return binding.root
    }

    private fun refresh() = viewModel.authorize(
        preferences.getInt(PROFILE_ID, 0),
        preferences.getString(PASSWORD, "") ?: ""
    )

    @SuppressLint("SetTextI18n")
    private fun onAuthorize(profile: Profile) = with(binding){
        Logger.d(OSS_TAG, "authorize complete")
        profileName.text = profile.name + " " + profile.surname
        profileGroup.text = getString(R.string.group) + " " + "undefined"
        profileScore.text = getString(R.string.balance) + " " + profile.capital
    }

    private fun onError(t: Throwable){
        Toast.makeText(requireContext(), t.message, Toast.LENGTH_LONG).show()
        Logger.d(OSS_TAG, t.message.toString())
    }
}