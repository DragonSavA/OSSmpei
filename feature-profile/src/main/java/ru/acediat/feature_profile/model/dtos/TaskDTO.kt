package ru.acediat.feature_profile.model.dtos

import com.google.gson.annotations.SerializedName
import java.io.Serializable

const val ONGOING = "ongoing"
const val IN_CHECKING = "onchecking"
const val PAYED = "payed"
const val CANCELED = "canceled"
const val PENALIZED = "penalized"
const val REFUSED = "refused"

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

    @SerializedName("status")
    var status: String? = null,

    @SerializedName("edit_before")
    var changeBefore: String? = null,

    @SerializedName("penalty")
    var penalty: String? = null,

    @SerializedName("coment_admin")
    var adminComment: String? = null,

    @SerializedName("comment")
    var comment: String? = null,

    @SerializedName("photo")
    var imageUrl: String? = null

): Serializable