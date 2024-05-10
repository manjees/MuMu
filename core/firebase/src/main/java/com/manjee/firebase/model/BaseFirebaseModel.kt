package com.manjee.firebase.model

import kotlinx.serialization.Serializable

@Serializable
data class BaseFirebaseModel<T> (
    val objects: T? = null,
    var message: String? = null
)