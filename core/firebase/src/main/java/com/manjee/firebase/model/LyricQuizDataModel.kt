package com.manjee.firebase.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class LyricQuizDataModel(
    @SerialName("answer") val answer: String,
    @SerialName("start_time") val startTime: Int,
    @SerialName("end_time") val endTime: Int,
    @SerialName("video_id") val videoId: String
)
