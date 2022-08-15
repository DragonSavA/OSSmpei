package ru.acediat.feature_profile.model

import android.os.Bundle
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.MutableLiveData
import io.reactivex.rxjava3.disposables.Disposable
import ru.acediat.core_android.BaseViewModel
import ru.acediat.feature_profile.model.dtos.ProductDTO
import javax.inject.Inject

class ShopViewModel: BaseViewModel() {

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

    fun getAllProducts(): Disposable = repository.getAllProducts()
        .subscribe({
            allProducts.postValue(it as ArrayList<ProductDTO>)
        }, {
            error.postValue(it)
        })

    fun getPopularProducts(): Disposable = repository.getPopularProducts()
        .subscribe({
            popularProducts.postValue(it as ArrayList<ProductDTO>)
        }, {
            error.postValue(it)
        })

    override fun restoreData(savedInstanceState: Bundle){
        getAllProducts()
        getPopularProducts()
    }
}