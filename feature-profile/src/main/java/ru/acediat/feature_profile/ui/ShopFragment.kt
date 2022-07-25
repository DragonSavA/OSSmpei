package ru.acediat.feature_profile.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.fragment.app.Fragment
import ru.acediat.feature_profile.model.dtos.ProductDTO
import ru.acediat.feature_profile.ui.adapters.ShopSectionsAdapter
import ru.acediat.feature_profile.model.ShopViewModel
import ru.acediat.feature_profile.databinding.FragmentShopBinding
import ru.acediat.feature_profile.di.ProfileComponent
import javax.inject.Inject

class ShopFragment : Fragment() {

    @Inject lateinit var sectionsAdapter: ShopSectionsAdapter

    private lateinit var binding: FragmentShopBinding

    private val viewModel: ShopViewModel = ViewModelProvider
        .NewInstanceFactory()
        .create(ShopViewModel::class.java)

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentShopBinding.inflate(inflater, container, false)
        inject()
        initViewModel()
        initViews()
        refresh()
        return binding.root
    }

    private fun inject() = with(ProfileComponent.init(requireContext())){
        inject(this@ShopFragment)
        inject(viewModel)
    }

    private fun initViewModel() = with(viewModel){
        setAllProductsObserver(viewLifecycleOwner, ::onAllProductsReceived)
        setPopularProductsObserver(viewLifecycleOwner, ::onPopularProductsReceived)
        setErrorObserver(viewLifecycleOwner, ::onError)
    }

    private fun initViews() = with(binding){
        backButton.setOnClickListener { requireActivity().onBackPressed() }
        sectionsAdapter.setOnProductClickListener(::onProductClick)
        sectionsAdapter.setOnRefreshListener(::onProductsRefresh)
        sectionsPager.adapter = sectionsAdapter
        sectionTabs.setupWithViewPager(sectionsPager)
    }

    private fun refresh() = with(viewModel){
        getAllProducts()
        getPopularProducts()
    }

    private fun onAllProductsReceived(products: ArrayList<ProductDTO>) =
        sectionsAdapter.setAllProducts(products)

    private fun onPopularProductsReceived(products: ArrayList<ProductDTO>) =
        sectionsAdapter.setPopularProducts(products)

    private fun onProductClick(product: ProductDTO){
        //TODO: реализовать переход на подробную информацию о товаре
    }

    private fun onProductsRefresh() = refresh()

    private fun onError(error : Throwable){}

    companion object {
        @JvmStatic
        fun newInstance() = ShopFragment()
    }
}