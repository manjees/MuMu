package com.manjee.core.datastore.mapper

import com.manjee.core.datastore.model.MyArtistData
import com.manjee.model.Artist

fun MyArtistData.toArtist() = Artist(
    id = this.id,
    name = this.name,
    score = 0,
    isMyArtist = true
)