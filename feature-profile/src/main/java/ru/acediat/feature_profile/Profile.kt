package ru.acediat.feature_profile

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class Profile(
    @SerializedName("id")
    var id: Int = 0,
    var hashPass: String = "",
    @SerializedName("name")
    var name: String = "Name",
    @SerializedName("surname")
    var surname: String = "Surname",
    @SerializedName("group")
    var group: String = "",
    @SerializedName("email")
    var email: String = "",
    @SerializedName("capital")
    var capital: Int = 0
): Serializable