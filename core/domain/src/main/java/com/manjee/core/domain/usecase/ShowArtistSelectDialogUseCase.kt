package com.manjee.core.domain.usecase

import com.manjee.core.datastore.datasource.CachingPreferenceDataSource
import com.manjee.firebase.database.RankingDatabase
import com.manjee.model.Artist
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.supervisorScope
import javax.inject.Inject

class ShowArtistSelectDialogUseCase @Inject constructor(
    private val cachingDataSource: CachingPreferenceDataSource,
    private val rankingDatabase: RankingDatabase
) {

    suspend operator fun invoke(): Flow<Triple<Boolean, Artist?, List<Artist>>> {
        var myArtistData: Artist? = null
        var isRequestVisible = false
        val rankingList: List<Artist>

        supervisorScope {
            val myArtistDataDeferred = async {
                myArtistData = cachingDataSource.myArtistData.first()
            }

            val isRequestVisibleDeferred = async {
                isRequestVisible = cachingDataSource.isRequestVisible.first()
            }

            val rankingListDeferred = async {
                rankingDatabase.getArtistData()
            }

            awaitAll(myArtistDataDeferred, isRequestVisibleDeferred, rankingListDeferred)

            rankingList = rankingListDeferred.await()
        }

        val result = myArtistData?.let {
            false
        } ?: run {
            isRequestVisible
        }

        return flowOf(Triple(result, myArtistData, rankingList))
    }
}