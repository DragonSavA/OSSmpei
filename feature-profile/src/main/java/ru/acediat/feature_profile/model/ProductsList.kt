package ru.acediat.feature_profile.model

import ru.acediat.feature_profile.model.dtos.ProductDTO

class ProductsList {

    val all = ArrayList<ProductDTO>()
    val popular = ArrayList<ProductDTO>()

    fun clear(){
        all.clear()
        popular.clear()
    }
}