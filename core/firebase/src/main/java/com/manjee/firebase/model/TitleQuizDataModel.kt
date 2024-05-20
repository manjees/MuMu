package com.manjee.firebase.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class TitleQuizDataModel(
    @SerialName("title") val title: String,
    @SerialName("artist") val artist: String,
    @SerialName("video_id") val videoId: String
)
