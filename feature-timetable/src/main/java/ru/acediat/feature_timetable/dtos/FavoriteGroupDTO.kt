package ru.acediat.feature_timetable.dtos

import com.google.gson.annotations.SerializedName

data class FavoriteGroupDTO(
    @SerializedName("user_id")
    val userId: Int = 0,
    @SerializedName("group_name")
    val group: String = ""
)