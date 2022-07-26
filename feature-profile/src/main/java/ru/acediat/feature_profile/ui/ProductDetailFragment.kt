package ru.acediat.feature_profile.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import com.squareup.picasso.Picasso
import ru.acediat.core_android.Logger
import ru.acediat.core_android.OSS_TAG
import ru.acediat.feature_profile.R
import ru.acediat.feature_profile.databinding.FragmentProductDetailBinding
import ru.acediat.feature_profile.di.ProfileComponent
import ru.acediat.feature_profile.model.ProductDetailViewModel
import ru.acediat.feature_profile.model.dtos.CapitalDTO
import ru.acediat.feature_profile.model.dtos.ProductDTO
import javax.inject.Inject

class ProductDetailFragment : Fragment() {

    @Inject lateinit var picasso: Picasso

    private val viewModel : ProductDetailViewModel = ViewModelProvider
        .NewInstanceFactory()
        .create(ProductDetailViewModel::class.java)

    private lateinit var binding: FragmentProductDetailBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentProductDetailBinding.inflate(inflater, container, false)
        inject()
        initViewModel()
        bindViews()
        return binding.root
    }

    private fun inject() = with(ProfileComponent.init(requireContext())){
        inject(viewModel)
        inject(this@ProductDetailFragment)
    }

    private fun initViewModel() = with(viewModel){
        setProduct(arguments?.get(PRODUCT_BUNDLE) as ProductDTO)
        setPurchaseObserver(viewLifecycleOwner, ::onPurchaseComplete)
        setErrorObserver(viewLifecycleOwner, ::onError)
    }

    private fun bindViews() = with(binding){
        toolbar.shopTitle.text = getString(R.string.product)
        productCost.text = viewModel.getCost().toString()
        name.text = viewModel.getName()
        description.text =
            if(viewModel.getDescription() != "")
                viewModel.getDescription()
            else
                getString(R.string.description_undefined)
        toolbar.backButton.setOnClickListener { requireActivity().onBackPressed() }
        buy.setOnClickListener { viewModel.buyProduct() }
        picasso.load(viewModel.getImageUrl())
            .fit()
            .centerCrop()
            .into(productImage)
    }

    private fun onPurchaseComplete(capitalDTO: CapitalDTO){
        Logger.d(OSS_TAG, "balance after buying: ${capitalDTO.capital}")
    }

    private fun onError(throwable: Throwable){
        Logger.e(OSS_TAG, throwable.message.toString())
    }


}