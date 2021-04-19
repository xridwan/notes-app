package com.xridwan.notes.presenter

import com.google.firebase.database.*
import com.xridwan.notes.model.Note

class MainPresenter(val mainView: MainView) {
    fun get() {
        val ref = FirebaseDatabase.getInstance().getReference("Notes")
        ref.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val noinline = mutableListOf<Note>()
                if (snapshot.exists()){
                    noinline.clear()
                    for (ds in snapshot.children) {
                        val note = ds.getValue(Note::class.java)
                        if (note != null) {
                            noinline.add(note)
                            mainView.onSuccess("", noinline)
                        }
                    }
                }
            }

            override fun onCancelled(error: DatabaseError) {
                mainView.onFailure(error.message)
            }
        })
    }

    fun delete(note: Note){
        val ref = FirebaseDatabase.getInstance().getReference("Notes")
            .child(note.id.toString())
        ref.removeValue()
    }
}