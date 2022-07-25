package ru.acediat.feature_profile

import io.reactivex.rxjava3.schedulers.Schedulers
import ru.acediat.feature_profile.apis.ShopApi
import javax.inject.Inject

class ShopRepository @Inject constructor(
    private val api: ShopApi
) {

    companion object {
        const val POPULAR_ITEM = "PopularItem"
        const val ALL_ITEM = "RegularItem"
    }

    fun getAllProducts() = api.loadProducts(ALL_ITEM)
        .subscribeOn(Schedulers.io())
        .observeOn(Schedulers.io())

    fun getPopularProducts() = api.loadProducts(POPULAR_ITEM)
        .subscribeOn(Schedulers.io())
        .observeOn(Schedulers.io())

    fun buyProduct(userId: Int, productId: Int) = api.buyProduct(
        userId.toString(),
        productId.toString()
    ).subscribeOn(Schedulers.io())
        .observeOn(Schedulers.io())

    fun getUserShopInfo(userId: Int) = api.getUserShopInfo(userId.toString())
        .subscribeOn(Schedulers.io())
        .observeOn(Schedulers.io())
}