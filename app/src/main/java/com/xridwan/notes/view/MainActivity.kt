package com.xridwan.notes.view

import android.app.AlertDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.widget.Toast
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.xridwan.notes.model.Note
import com.xridwan.notes.adapter.NoteAdapter
import com.xridwan.notes.databinding.ActivityMainBinding
import com.xridwan.notes.databinding.DeleteItemLayoutBinding
import com.xridwan.notes.presenter.MainPresenter
import com.xridwan.notes.presenter.MainView

class MainActivity : AppCompatActivity(), MainView {

    private lateinit var binding: ActivityMainBinding
    private lateinit var mainPresenter: MainPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        mainPresenter = MainPresenter(this)
        mainPresenter.get()

        binding.imgAdd.setOnClickListener {
            startActivity(Intent(this, CreateNoteActivity::class.java))
        }
    }

    override fun onSuccess(message: String, noinline: MutableList<Note>) {
        val adapter = NoteAdapter(this, noinline)

        binding.rvNote.setHasFixedSize(true)
        binding.rvNote.layoutManager =
            StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)
        binding.rvNote.adapter = adapter

        adapter.setOnItemCLickCallBack(object : NoteAdapter.OnItemClickCallBack {
            override fun onItemClicked(note: Note) {
                selectedNote(note)
            }

            override fun onItemLongClicked(note: Note) {
                deletedNote(note)
            }
        })
    }

    override fun onFailure(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

    private fun deletedNote(note: Note) {
        val binding = DeleteItemLayoutBinding.inflate(LayoutInflater.from(this))
        val builder = AlertDialog.Builder(this)
        builder.setView(binding.root)

        val alert = builder.create()
        alert.window?.setBackgroundDrawableResource(android.R.color.transparent)

        binding.btnYes.setOnClickListener {
            mainPresenter.delete(note)
            alert.dismiss()
            Toast.makeText(this, "Note deleted", Toast.LENGTH_SHORT).show()
        }

        binding.btnNo.setOnClickListener {
            alert.dismiss()
        }

        alert.setCancelable(false)
        alert.show()
    }

    private fun selectedNote(note: Note) {
        val intent = Intent(this, CreateNoteActivity::class.java)
        intent.putExtra(CreateNoteActivity.EXTRA_ID, note.id)
        intent.putExtra(CreateNoteActivity.EXTRA_TITLE, note.title)
        intent.putExtra(CreateNoteActivity.EXTRA_DESC, note.desc)
        startActivity(intent)
    }
}