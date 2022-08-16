package ru.acediat.feature_profile.model.dtos

import com.google.gson.annotations.SerializedName

data class TaskDTO(

    @SerializedName("id")
    var id: Int? = 0,

    @SerializedName("description_full")
    var fullDescription: String,

    @SerializedName("description_short")
    var shortDescription: String,

    @SerializedName("start")
    var startDate: String,

    @SerializedName("end")
    var endDate: String,

    @SerializedName("refuse_before")
    var refuseBefore: String,

    @SerializedName("place")
    var place: String,

    @SerializedName("reward")
    var reward: String,

    var status: String? = null,

    var changeBefore: String? = null,

    var penalty: String? = null
){
    override fun toString(): String = "$id: $shortDescription"
}