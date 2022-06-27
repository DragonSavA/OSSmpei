package ru.acediat.feature_feed.entities

import ru.acediat.core_network.DTO

data class Post(
    var name : String?,
    var imageSrc : String?,
    var postDate : String?,
    var description : String?,
    var content : String?,
) : DTO