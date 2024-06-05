package com.manjee.firebase.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ThemeDataModel(
    @SerialName("id") val id: String,
    @SerialName("theme_name") val themeName: String,
    @SerialName("description") val description: String,
    @SerialName("is_first") val isFirst: Boolean
)
