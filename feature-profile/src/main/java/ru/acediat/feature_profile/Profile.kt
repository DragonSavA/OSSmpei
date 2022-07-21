package ru.acediat.feature_profile

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class Profile(
    @SerializedName("id")
    var id: Int = 0,
    @SerializedName("name")
    var name: String = "",
    @SerializedName("surname")
    var surname: String = "",
    @SerializedName("group")
    var group: String = "",
    @SerializedName("email")
    var email: String = "",
    @SerializedName("capital")
    var capital: Int = 0,
    @SerializedName("image_src")
    var imageSrc: String = ""
): Serializable