package com.manjee.firebase.mapper

import com.manjee.firebase.model.TitleQuizDataModel
import com.manjee.model.Title

fun TitleQuizDataModel.toTitle(): Title {

    return Title(
        title = this.title,
        artist = this.artist,
        videoId = this.videoId
    )
}