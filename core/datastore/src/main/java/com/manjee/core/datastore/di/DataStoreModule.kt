package com.manjee.core.datastore.di

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import com.manjee.core.datastore.DataStoreConst.CACHING_DATASTORE
import com.manjee.core.datastore.datasource.CachingPreferenceDataSource
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Named
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DataStoreModule {
    private const val CACHING_DATASTORE_NAME = "caching_datastore"

    private val Context.cachingDataStore by preferencesDataStore(name = CACHING_DATASTORE_NAME)

    @Provides
    @Singleton
    @Named(CACHING_DATASTORE)
    fun provideCachingDataStore(
        @ApplicationContext context: Context
    ): DataStore<Preferences> = context.cachingDataStore

    @Provides
    @Singleton
    fun provideCachingPreferenceDataSource(
        @Named(CACHING_DATASTORE) cachingDataStore: DataStore<Preferences>
    ): CachingPreferenceDataSource {
        return CachingPreferenceDataSource(cachingDataStore)
    }
}