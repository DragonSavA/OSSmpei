package ru.acediat.feature_profile.model.dtos

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class ProductDTO(
    @SerializedName("id")
    val id: Int? = 0,
    @SerializedName("title")
    val name: String = "",
    @SerializedName("description")
    val description: String = "",
    @SerializedName("price")
    val price: Int = 0,
    @SerializedName("image_src")
    val imageUrl: String = "",
    @SerializedName("counter")
    val counter: Int = 0,
    @SerializedName("quantity")
    val quantity: Int = 0
) : Parcelable