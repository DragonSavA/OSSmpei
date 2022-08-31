package ru.acediat.feature_profile.model.dtos

import com.google.gson.annotations.SerializedName

data class GroupDTO(
    @SerializedName("user_id")
    val userId: Int = 0,
    @SerializedName("group_name")
    val group: String = ""
)