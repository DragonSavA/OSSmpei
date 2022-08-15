package ru.acediat.feature_profile.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import ru.acediat.core_android.BaseFragment
import ru.acediat.feature_profile.model.dtos.ProductDTO
import ru.acediat.feature_profile.ui.adapters.ShopSectionsAdapter
import ru.acediat.feature_profile.model.ShopViewModel
import ru.acediat.feature_profile.databinding.FragmentShopBinding
import ru.acediat.feature_profile.di.ProfileComponent
import ru.acediat.core_navigation.R as navR
import javax.inject.Inject

const val PRODUCT_BUNDLE = "product"

class ShopFragment : BaseFragment<FragmentShopBinding, ShopViewModel>() {

    @Inject lateinit var sectionsAdapter: ShopSectionsAdapter

    override val viewModel: ShopViewModel = ViewModelProvider
        .NewInstanceFactory()
        .create(ShopViewModel::class.java)

    override fun instanceBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentShopBinding = FragmentShopBinding.inflate(inflater, container, false)

    override fun inject() = with(ProfileComponent.init(requireContext())){
        inject(this@ShopFragment)
        inject(viewModel)
    }

    override fun prepareViewModel() = with(viewModel){
        setAllProductsObserver(viewLifecycleOwner, ::onAllProductsReceived)
        setPopularProductsObserver(viewLifecycleOwner, ::onPopularProductsReceived)
        setErrorObserver(viewLifecycleOwner, ::onError)
    }

    override fun prepareViews() = with(binding){
        shopToolbar.backButton.setOnClickListener { requireActivity().onBackPressed() }
        sectionsAdapter.setOnProductClickListener(::onProductClick)
        sectionsAdapter.setOnRefreshListener(::refresh)
        sectionsPager.adapter = sectionsAdapter
        sectionTabs.setupWithViewPager(sectionsPager)
    }

    override fun refresh(): Unit = with(viewModel){
        getAllProducts()
        getPopularProducts()
    }

    private fun onAllProductsReceived(products: ArrayList<ProductDTO>) =
        sectionsAdapter.setAllProducts(products)

    private fun onPopularProductsReceived(products: ArrayList<ProductDTO>) =
        sectionsAdapter.setPopularProducts(products)

    private fun onProductClick(product: ProductDTO) = findNavController().navigate(
        navR.id.productDetailFragment,
        bundleOf(PRODUCT_BUNDLE to product)
    )

    private fun onError(error : Throwable){}//TODO: сделать обработку ошибок
}