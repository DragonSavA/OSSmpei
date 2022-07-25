package ru.acediat.feature_profile

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import javax.inject.Inject

class ShopViewModel: ViewModel() {

    @Inject lateinit var repository: ShopRepository

    private val popularProducts = MutableLiveData<ArrayList<ProductDTO>>()
    private val allProducts = MutableLiveData<ArrayList<ProductDTO>>()
    private val error = MutableLiveData<Throwable>()

    fun setPopularProductsObserver(owner: LifecycleOwner, observer: (ArrayList<ProductDTO>) -> Unit) =
        popularProducts.observe(owner, observer)

    fun setAllProductsObserver(owner: LifecycleOwner, observer: (ArrayList<ProductDTO>) -> Unit) =
        allProducts.observe(owner, observer)

    fun setErrorObserver(owner: LifecycleOwner, observer: (Throwable) -> Unit) =
        error.observe(owner, observer)

    fun getAllProducts() = repository.getAllProducts()
        .subscribe({
            allProducts.postValue(it as ArrayList<ProductDTO>)
        }, {
            error.postValue(it)
        })

    fun getPopularProducts() = repository.getPopularProducts()
        .subscribe({
            popularProducts.postValue(it as ArrayList<ProductDTO>)
        }, {
            error.postValue(it)
        })
}