package ru.acediat.feature_map

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.DialogFragment
import ru.acediat.feature_map.databinding.DialogPlacemarkBinding

const val PLACEMARK_NAME = "name"
const val PLACEMARK_DESCRIPTION = "description"

class PlacemarkDialog: DialogFragment() {

    private lateinit var binding: DialogPlacemarkBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DialogPlacemarkBinding.inflate(inflater, container, false)
        arguments?.let {
            binding.name.text = it[PLACEMARK_NAME] as String
            val description = it[PLACEMARK_DESCRIPTION] as String
            if(description != "")
                binding.description.text = description
            else
                binding.description.isVisible = false
        }
        binding.okButton.setOnClickListener { dismiss() }
        return binding.root
    }

    companion object{

        fun newInstance(bundle: Bundle) = PlacemarkDialog().apply {
            this.arguments = bundle
        }

    }
}