package com.manjee.firebase.di

import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.manjee.firebase.FirebaseConst.BILLBOARD_DATABASE
import com.manjee.firebase.FirebaseConst.LYRIC_DATABASE
import com.manjee.firebase.FirebaseConst.RANKING_DATABASE
import com.manjee.firebase.FirebaseConst.THEME_DATABASE
import com.manjee.firebase.FirebaseConst.TITLE_DATABASE
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
        return getDatabaseReference("lyric")
    }

    @Singleton
    @Named(TITLE_DATABASE)
    @Provides
    fun provideTitleDatabase() : DatabaseReference {
        return getDatabaseReference("title")
    }

    @Singleton
    @Named(RANKING_DATABASE)
    @Provides
    fun provideRankingDatabase() : DatabaseReference {
        return getDatabaseReference("ranking")
    }

    @Singleton
    @Named(THEME_DATABASE)
    @Provides
    fun provideThemeDatabase() : DatabaseReference {
        return getDatabaseReference("theme")
    }

    @Singleton
    @Named(BILLBOARD_DATABASE)
    @Provides
    fun provideBillboardDatabase() : DatabaseReference {
        return getDatabaseReference("billboard")
    }

    private fun getDatabaseReference(child: String): DatabaseReference {
        return Firebase.database.reference.child(child).child("data")
    }
}