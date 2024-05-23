package com.manjee.firebase.mapper

import com.manjee.firebase.model.RankingDataModel
import com.manjee.model.Artist

fun RankingDataModel.toArtist() = Artist(
    id = this.id,
    name = this.name,
    score = this.score
)