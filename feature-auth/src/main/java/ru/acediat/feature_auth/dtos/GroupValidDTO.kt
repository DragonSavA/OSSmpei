package ru.acediat.feature_auth.dtos

import com.google.gson.annotations.SerializedName

data class GroupValidDTO(
    @SerializedName("isValid")
    val isGroupValid : Boolean
)