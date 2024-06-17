package com.manjee.firebase.database

import com.google.firebase.database.DatabaseReference
import com.manjee.firebase.FirebaseConst.THEME_DATABASE
import com.manjee.model.Theme
import kotlinx.coroutines.suspendCancellableCoroutine
import javax.inject.Inject
import javax.inject.Named
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException

class ThemeDatabase @Inject constructor(
    @Named(THEME_DATABASE) private val fireDb: DatabaseReference,
) {

    suspend fun getTheme(): List<Theme> = suspendCancellableCoroutine { const ->
        fireDb.get().addOnSuccessListener {
            val dataList = it.value as List<Map<String, Any>>?

            val themeList = dataList?.map { data ->
                Theme(
                    id = data["id"] as String,
                    themeName = data["theme_name"] as String,
                    description = data["description"] as String,
                    isFirst = data["is_first"] as Boolean,
                    isTitle = data["is_title"] as Boolean,
                    isShow = data["is_show"] as Boolean
                )
            }?.filter { theme -> theme.isShow } ?: emptyList()

            const.resume(themeList)
        }.addOnFailureListener {
            const.resumeWithException(it)
        }
    }
}