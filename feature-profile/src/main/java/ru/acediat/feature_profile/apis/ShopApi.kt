package ru.acediat.feature_profile.apis

import io.reactivex.rxjava3.core.Single
import retrofit2.http.*
import ru.acediat.feature_profile.CapitalDTO
import ru.acediat.feature_profile.ProductDTO

interface ShopApi {

    @GET("/Android/shop.php")
    fun loadProducts(
        @Query("type") type: String
    ): Single<List<ProductDTO>>

    @POST("/Android/shop.php")
    @FormUrlEncoded
    fun buyProduct(
        @Field("userId") userId: String,
        @Field("productId") productId: String
    ): Single<CapitalDTO>

    @GET("/Android/shop.php?type=user_shop_info")
    fun getUserShopInfo(
        @Query("id") id: String
    ): Single<CapitalDTO>

}