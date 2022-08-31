package ru.acediat.feature_profile.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import com.squareup.picasso.Picasso
import ru.acediat.core_android.BaseFragment
import ru.acediat.feature_profile.databinding.FragmentSettingsBinding
import ru.acediat.feature_profile.di.ProfileComponent
import ru.acediat.feature_profile.model.SettingsViewModel
import ru.acediat.feature_profile.model.dtos.GroupDTO
import ru.acediat.feature_profile.ui.adapters.GroupsAdapter
import javax.inject.Inject

const val IMAGE_URL_BUNDLE = "image url"

class SettingsFragment: BaseFragment<FragmentSettingsBinding, SettingsViewModel>() {

    @Inject lateinit var groupsAdapter: GroupsAdapter
    @Inject lateinit var picasso: Picasso

    override val viewModel: SettingsViewModel = ViewModelProvider
        .NewInstanceFactory()
        .create(SettingsViewModel::class.java)

    override fun instanceBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentSettingsBinding =
        FragmentSettingsBinding.inflate(inflater, container, false)

    override fun inject() = with(ProfileComponent.init(requireContext())){
        inject(this@SettingsFragment)
        inject(viewModel)
    }

    override fun prepareViewModel() = with(viewModel){
        setGroupsObserver(viewLifecycleOwner, ::onGroupsReceived)
        setOnSaveCompleteObserver(viewLifecycleOwner, ::onGroupSaveComplete)
        setErrorObserver(viewLifecycleOwner, ::onError)
    }

    override fun prepareViews() = with(binding){
        addButton.setOnClickListener { onAddFavoriteButtonClick() }
        okButton.setOnClickListener { viewModel.setCurrentGroup(currentGroup.text.toString()) }
        backButton.setOnClickListener { requireActivity().onBackPressed() }
        currentGroup.setText(viewModel.getCurrentGroup())
        groupList.adapter = groupsAdapter
        refresh()
    }

    override fun refresh(){
        viewModel.getFavoriteGroups()
    }

    private fun onAddFavoriteButtonClick() =
        viewModel.saveGroup(binding.currentGroup.text.toString())

    private fun onGroupSaveComplete(group: String) {
        if(!groupsAdapter.containGroup(group))
            groupsAdapter.addItem(group)
        //TODO: сделать уведомление о том, что такая группа уже есть в else ветке
    }

    private fun onGroupsReceived(groups: ArrayList<String>) = groupsAdapter.setItems(groups)

    private fun onError(t: Throwable){}//TODO: сделать уведомление об ошибке
}