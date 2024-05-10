package com.manjee.firebase.di

import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.manjee.firebase.FirebaseConst.LYRIC_DATABASE
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Named
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
internal object FirebaseModule {

    @Singleton
    @Named(LYRIC_DATABASE)
    @Provides
    fun provideLyricDatabase() : DatabaseReference {
        return Firebase.database.reference.child("lyric").child("data")
    }
}