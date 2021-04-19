package com.xridwan.notes.presenter

import android.annotation.SuppressLint
import android.app.Activity
import android.text.TextUtils
import com.google.firebase.database.FirebaseDatabase
import com.xridwan.notes.model.Note
import java.text.SimpleDateFormat
import java.util.*

class CreatePresenter(private val createView: CreateView) {
    @SuppressLint("SimpleDateFormat")
    fun create(noteId: String? = null, title: String, desc: String, activity: Activity) {
        val simpleDateFormat = SimpleDateFormat("dd MMMM hh:mm")
        val date = simpleDateFormat.format(Date())

        if (!(TextUtils.isEmpty(title) || TextUtils.isEmpty(desc))) {
            val ref = FirebaseDatabase.getInstance().getReference("Notes")

            if (noteId == null) {
                val id = ref.push().key.toString()

                val notes = Note(id, title, desc, date)
                ref.child(id).setValue(notes).addOnCompleteListener {
                    activity.finish()
                }

            } else {
                val notes = Note(noteId, title, desc, date)
                ref.child(noteId.toString()).setValue(notes).addOnCompleteListener {
                    activity.finish()
                }
            }

            createView.onSuccess("Note created")
        }
    }
}