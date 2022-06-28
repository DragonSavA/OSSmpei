package ru.acediat.feature_timetable.dtos

import com.google.gson.annotations.SerializedName
import ru.acediat.core_network.DTO

data class GroupValidDTO(
    @SerializedName("isValid")
    val isGroupValid : Boolean
) : DTO