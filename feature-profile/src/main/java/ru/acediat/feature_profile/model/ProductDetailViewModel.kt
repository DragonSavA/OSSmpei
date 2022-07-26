package ru.acediat.feature_profile.model

import androidx.lifecycle.ViewModel
import ru.acediat.feature_profile.model.dtos.ProductDTO

class ProductDetailViewModel: ViewModel() {

    private var product: ProductDTO? = null

    fun setProduct(product: ProductDTO){
        this.product = product
    }

    fun getName() = product?.name ?: "null"

    fun getCost() = product?.price ?: 0

    fun getImageUrl() = product?.imageUrl ?: ""

    fun getDescription() = product?.description ?: ""

}