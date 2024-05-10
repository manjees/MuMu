package com.manjee.firebase.database

import android.util.Log
import com.google.firebase.database.DatabaseReference
import com.manjee.firebase.FirebaseConst.LYRIC_DATABASE
import com.manjee.firebase.mapper.toLyric
import com.manjee.firebase.model.BaseFirebaseModel
import com.manjee.firebase.model.LyricQuizDataModel
import com.manjee.model.BaseModel
import com.manjee.model.JsonParser
import com.manjee.model.LyricQuiz
import javax.inject.Inject
import javax.inject.Named

class LyricDatabase @Inject constructor(
    @Named(LYRIC_DATABASE) private val fireDb: DatabaseReference
) {
    operator fun invoke(callback: (BaseModel<List<LyricQuiz>>) -> Unit) {
        var data: BaseFirebaseModel<List<LyricQuizDataModel>>

        fireDb.get().addOnSuccessListener { snapshot ->
            snapshot.value?.let { value ->
                data =
                    JsonParser.parse<BaseFirebaseModel<List<LyricQuizDataModel>>>(value.toString())

                val mappingData = data.objects?.map { lyricQuizDataModel ->
                    lyricQuizDataModel.toLyric()
                }

                callback(BaseModel(data = mappingData))
            } ?: run {
                // If snapshot value is null
                callback(BaseModel(error = "Snapshot value is null"))
            }
        }.addOnFailureListener { exception ->
            // Return failure BaseModel
            callback(BaseModel(error = exception.message))
        }
    }
}