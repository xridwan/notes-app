package com.xridwan.notes.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Note(
    val id: String? = null,
    val title: String? = null,
    val desc: String? = null,
    val date: String? = null,
) : Parcelable