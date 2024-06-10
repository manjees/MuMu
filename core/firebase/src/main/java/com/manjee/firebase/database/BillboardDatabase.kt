package com.manjee.firebase.database

import android.util.Log
import com.google.firebase.database.DatabaseReference
import com.manjee.firebase.FirebaseConst.BILLBOARD_DATABASE
import com.manjee.firebase.FirebaseConst.LYRIC_DATABASE
import com.manjee.firebase.FirebaseConst.TITLE_DATABASE
import com.manjee.firebase.mapper.toLyric
import com.manjee.firebase.mapper.toTitle
import com.manjee.firebase.model.BaseFirebaseModel
import com.manjee.firebase.model.LyricQuizDataModel
import com.manjee.firebase.model.TitleQuizDataModel
import com.manjee.model.BaseModel
import com.manjee.model.JsonParser
import com.manjee.model.LyricQuiz
import com.manjee.model.TitleQuiz
import javax.inject.Inject
import javax.inject.Named

class BillboardDatabase @Inject constructor(
    @Named(BILLBOARD_DATABASE) private val fireDb: DatabaseReference
) {
    operator fun invoke(callback: (BaseModel<TitleQuiz>) -> Unit) {
        var data: BaseFirebaseModel<List<TitleQuizDataModel>>

        fireDb.get().addOnSuccessListener { snapshot ->
            snapshot.value?.let { value ->
                data =
                    JsonParser.parse<BaseFirebaseModel<List<TitleQuizDataModel>>>(value.toString())

                val mappingData = data.objects?.map { titleQuizDataModel ->
                    titleQuizDataModel.toTitle()
                }?.shuffled() ?: listOf()

                val choiceList = mappingData.map { titleQuiz ->
                    titleQuiz.title
                }

                callback(BaseModel(data = TitleQuiz(quiz = mappingData, choiceList = choiceList)))
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