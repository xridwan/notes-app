package com.xridwan.notes.presenter

import com.xridwan.notes.model.Note

interface MainView {
    fun onSuccess(message: String, noinline : MutableList<Note>)
    fun onFailure(message: String)
}