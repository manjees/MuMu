package com.manjee.core.domain.usecase

import com.manjee.core.datastore.datasource.CachingPreferenceDataSource
import com.manjee.firebase.database.RankingDatabase
import com.manjee.firebase.database.ThemeDatabase
import com.manjee.model.Artist
import com.manjee.model.MainScreenData
import com.manjee.model.Theme
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.supervisorScope
import javax.inject.Inject

class ShowArtistSelectDialogUseCase @Inject constructor(
    private val cachingDataSource: CachingPreferenceDataSource,
    private val rankingDatabase: RankingDatabase,
    private val themeDatabase: ThemeDatabase
) {

    suspend operator fun invoke(): Flow<MainScreenData> {
        var myArtistData: Artist? = null
        var isRequestVisible = false
        val rankingList: List<Artist>
        val themeList: List<Theme>

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

            val themeListDeferred = async {
                themeDatabase.getTheme()
            }

            awaitAll(myArtistDataDeferred, isRequestVisibleDeferred, rankingListDeferred, themeListDeferred)

            rankingList = rankingListDeferred.await()
            themeList = themeListDeferred.await()
        }

        val result = myArtistData?.let {
            false
        } ?: run {
            isRequestVisible
        }

        return flowOf(
            MainScreenData(
                isRequestVisible = result,
                myArtist = myArtistData,
                rankingList = rankingList,
                themeList = themeList
            )
        )
    }
}