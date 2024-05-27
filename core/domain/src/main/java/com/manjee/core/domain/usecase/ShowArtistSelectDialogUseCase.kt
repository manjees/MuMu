package com.manjee.core.domain.usecase

import android.util.Log
import com.manjee.core.datastore.datasource.CachingPreferenceDataSource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flowOf
import javax.inject.Inject

class ShowArtistSelectDialogUseCase @Inject constructor(
    private val cachingDataSource: CachingPreferenceDataSource
) {

    suspend operator fun invoke(): Flow<Boolean> {
        val myArtistData = cachingDataSource.myArtistData.first()
        val isRequestVisible = cachingDataSource.isRequestVisible.first()

        Log.d("@@@@", "myArtistData: $myArtistData isRequestVisible: $isRequestVisible")

        val result = myArtistData?.let {
            false
        } ?: run {
            isRequestVisible
        }

        return flowOf(result)
    }
}