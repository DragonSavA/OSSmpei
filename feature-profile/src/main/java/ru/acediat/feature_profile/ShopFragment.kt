package ru.acediat.feature_profile

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.fragment.app.Fragment
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
        sectionsPager.adapter = sectionsAdapter
        sectionTabs.setupWithViewPager(sectionsPager)
    }

    private fun onAllProductsReceived(products: ArrayList<ProductDTO>) =
        sectionsAdapter.setAllProducts(products)

    private fun onPopularProductsReceived(products: ArrayList<ProductDTO>) =
        sectionsAdapter.setPopularProducts(products)

    private fun onProductClick(product: ProductDTO){
        //TODO: реализовать переход на подробную информацию о товаре
    }

    private fun onProductsRefresh(){
        //TODO: реализовать обновление списков товара
    }

    private fun onError(error : Throwable){}

    companion object {
        @JvmStatic
        fun newInstance() = ShopFragment()
    }
}