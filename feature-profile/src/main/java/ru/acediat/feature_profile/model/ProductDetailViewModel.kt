package ru.acediat.feature_profile.model

import android.content.SharedPreferences
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import ru.acediat.core_android.PROFILE_ID
import ru.acediat.feature_profile.model.dtos.CapitalDTO
import ru.acediat.feature_profile.model.dtos.ProductDTO
import javax.inject.Inject

class ProductDetailViewModel: ViewModel() {

    @Inject lateinit var repository: ShopRepository
    @Inject lateinit var preferences: SharedPreferences

    private var product: ProductDTO? = null
    private val purchase = MutableLiveData<CapitalDTO>()
    private val error = MutableLiveData<Throwable>()

    fun setPurchaseObserver(owner: LifecycleOwner, observer: (CapitalDTO) -> Unit) =
        purchase.observe(owner, observer)

    fun setErrorObserver(owner: LifecycleOwner, observer: (Throwable) -> Unit) =
        error.observe(owner, observer)

    fun setProduct(product: ProductDTO){
        this.product = product
    }

    fun getName() = product?.name ?: "null"

    fun getCost() = product?.price ?: 0

    fun getImageUrl() = product?.imageUrl ?: ""

    fun getDescription() = product?.description ?: ""

    fun buyProduct() = repository.buyProduct(getUserId(), product?.id ?: -1)
        .subscribe({
            purchase.postValue(it)
        }, {
            error.postValue(it)
        })

    private fun getUserId() = preferences.getInt(PROFILE_ID, 0)

}