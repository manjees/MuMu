package com.manjee.model

data class BaseModel<T>(
    var data: T? = null,
    var error: String? = null
)
