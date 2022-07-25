package ru.acediat.feature_profile.di

import com.squareup.picasso.Picasso
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit
import ru.acediat.core_android.di.AndroidModule
import ru.acediat.core_network.NetworkModule
import ru.acediat.core_network.buildApi
import ru.acediat.feature_profile.ui.adapters.ProductsAdapter
import ru.acediat.feature_profile.apis.ShopApi
import ru.acediat.feature_profile.model.ShopRepository
import ru.acediat.feature_profile.ui.adapters.ShopSectionsAdapter

@Module(includes = [NetworkModule::class, AndroidModule::class])
class ShopModule {

    @Provides
    fun provideShopApi(builder : Retrofit.Builder) = builder.buildApi<ShopApi>()

    @Provides
    fun provideShopRepository(api: ShopApi) = ShopRepository(api)

    @Provides
    fun provideProductsAdapter(picasso: Picasso) = ProductsAdapter(picasso)

    @Provides
    fun provideShopSectionsAdapter(
        allProductsAdapter: ProductsAdapter,
        popularProductsAdapter: ProductsAdapter
    ) = ShopSectionsAdapter(allProductsAdapter, popularProductsAdapter)
}