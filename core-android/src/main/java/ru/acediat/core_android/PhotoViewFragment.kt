package ru.acediat.core_android

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.squareup.picasso.Picasso
import ru.acediat.core_android.databinding.FragmentPhotoViewBinding

const val PHOTO_URL = "photo url"

class PhotoViewFragment: Fragment() {

    private lateinit var picasso: Picasso
    private lateinit var imageUrl: String

    private lateinit var binding: FragmentPhotoViewBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View{
        picasso = Picasso.Builder(requireContext()).build()
        binding = FragmentPhotoViewBinding.inflate(inflater, container, false)
        binding.backButton.setOnClickListener { requireActivity().onBackPressed() }
        imageUrl = arguments?.getString(PHOTO_URL) ?: ""
        picasso.load(imageUrl)
            .fit()
            .centerInside()
            .into(binding.photo)
        return binding.root
    }
}