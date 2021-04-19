package com.xridwan.notes.presenter

interface CreateView {
    fun onSuccess(message: String)
    fun onFailure(message: String)
}