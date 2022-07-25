package ru.acediat.feature_profile

import android.view.View
import android.view.ViewGroup
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import ru.acediat.core_android.BasePagerAdapter
import ru.acediat.core_res.gridRecyclerView
import ru.acediat.core_res.loadingFrame
import ru.acediat.core_res.linearRecyclerView
import javax.inject.Inject

class ShopSectionsAdapter @Inject constructor(
    private val allProductsAdapter: ProductsAdapter,
    private val popularProductsAdapter: ProductsAdapter
) : BasePagerAdapter<ProductsList>() {

    private var onRefresh: () -> Unit = {}
    private var onProductClick: (ProductDTO) -> Unit = {}

    fun setPopularProducts(products: ArrayList<ProductDTO>){
        setData(ProductsList().apply {
            popular.addAll(products)
            all.addAll(data?.all ?: listOf())
        })
        notifyDataSetChanged()
    }

    fun setAllProducts(products: ArrayList<ProductDTO>){
        setData(ProductsList().apply {
            all.addAll(products)
            popular.addAll(data?.popular ?: listOf())
        })
        notifyDataSetChanged()
    }

    fun setOnRefreshListener(onRefresh: () -> Unit){
        this.onRefresh = onRefresh
    }

    fun setOnProductClickListener(onProductClick: (ProductDTO) -> Unit){
        this.onProductClick = onProductClick
    }

    override fun getCount(): Int = 2

    override fun getPageTitle(position: Int): CharSequence = when(position){
        0 -> "Популярные"
        1 -> "Все"
        else -> "undefined"
    }

    override fun instantiateItem(container: ViewGroup, position: Int): Any =
        if(data == null)
            instantiateLoading(container)
        else if(position == 1)
            instantiateProductsList(container, data!!.all, allProductsAdapter)
        else
            instantiateProductsList(container, data!!.popular, popularProductsAdapter)


    override fun instantiateLoading(container: ViewGroup): View = with(loadingFrame(container)) {
        container.addView(this)
        return this
    }

    private fun instantiateProductsList(
        container: ViewGroup,
        list: ArrayList<ProductDTO>,
        productsAdapter: ProductsAdapter
    ): View = with(SwipeRefreshLayout(container.context)){
        layoutParams = ViewGroup.LayoutParams(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.MATCH_PARENT
        )
        addView(gridRecyclerView(context, 2).apply {
            adapter = productsAdapter.apply {
                addItems(list)
                setOnProductClick(onProductClick)
            }
        })
        setOnRefreshListener { onRefresh() }
        container.addView(this)
        return@with this
    }
}