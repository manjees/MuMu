package com.manjee.firebase.database

import android.util.Log
import com.google.firebase.database.DatabaseReference
import com.manjee.firebase.FirebaseConst.RANKING_DATABASE
import com.manjee.firebase.model.RankingDataModel
import javax.inject.Inject
import javax.inject.Named

class RankingDatabase @Inject constructor(
    @Named(RANKING_DATABASE) private val fireDb: DatabaseReference
) {

    fun initData() {
        val dataList = listOf(RankingDataModel(0, "BTS", 0),RankingDataModel(1, "SUZY", 0))

        val dataMap = dataList.map { rankingDataModel ->
            rankingDataModel.toMap()
        }

        fireDb.setValue(dataMap)
    }

    fun updateData() {
        fireDb.child("1").setValue(RankingDataModel(1, "SUZY", 1).toMap())
    }
}