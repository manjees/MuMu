package com.manjee.firebase.mapper

import com.manjee.firebase.model.ThemeDataModel
import com.manjee.model.Theme

fun ThemeDataModel.toTheme(): Theme {
    return Theme(
        id = this.id,
        themeName = this.themeName,
        description = this.description,
        isFirst = this.isFirst
    )
}