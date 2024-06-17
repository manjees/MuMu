package com.manjee.model

import androidx.compose.runtime.Stable

@Stable
data class Theme(
    val id: String,
    val themeName: String,
    val description: String,
    val isFirst: Boolean,
    val isTitle: Boolean,
    val isShow: Boolean
)
