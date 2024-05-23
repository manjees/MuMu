package com.manjee.core.datastore.datasource

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import com.manjee.core.datastore.DataStoreConst.CACHING_DATASTORE
import com.manjee.core.datastore.model.MyArtistData
import com.manjee.core.datastore.model.toMyArtist
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Named

class CachingPreferenceDataSource @Inject constructor(
    @Named(CACHING_DATASTORE) private val cachingDataStore: DataStore<Preferences>
) {
    private object PreferenceKeys {
        val MY_ARTIST = stringPreferencesKey("my_artist")
    }

    val myArtistData = cachingDataStore.data.map { preferences ->
        preferences[PreferenceKeys.MY_ARTIST]?.toMyArtist()
    }

    suspend fun updateMyArtistData(myArtistData: MyArtistData) {
        cachingDataStore.edit { preferences ->
            preferences[PreferenceKeys.MY_ARTIST] = myArtistData.toString()
        }
    }
}