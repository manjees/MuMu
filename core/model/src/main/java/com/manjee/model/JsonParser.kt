package com.manjee.model

import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

object JsonParser {

    val json = Json {
        ignoreUnknownKeys = true
    }

    inline fun <reified T> parse(jsonString: String): T {
        return json.decodeFromString(jsonString)
    }

    inline fun <reified T> stringify(data: T): String {
        return json.encodeToString(data)
    }
}