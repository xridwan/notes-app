package com.xridwan.notes.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.xridwan.notes.databinding.ActivityCreateNoteBinding
import com.xridwan.notes.presenter.CreatePresenter
import com.xridwan.notes.presenter.CreateView

class CreateNoteActivity : AppCompatActivity(), CreateView {

    private lateinit var binding: ActivityCreateNoteBinding
    private lateinit var createPresenter: CreatePresenter

    private var noteId: String? = null

    companion object {
        const val EXTRA_ID = "extra_id"
        const val EXTRA_TITLE = "extra_title"
        const val EXTRA_DESC = "extra_desc"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCreateNoteBinding.inflate(layoutInflater)
        setContentView(binding.root)

        createPresenter = CreatePresenter(this)

        noteId = intent.getStringExtra(EXTRA_ID)

        if (noteId != null) {
            binding.etTitle.setText(intent.getStringExtra(EXTRA_TITLE))
            binding.etDesc.setText(intent.getStringExtra(EXTRA_DESC))
        }

        binding.btnCreate.setOnClickListener {
            createNote()
        }

        binding.imgBack.setOnClickListener {
            onBackPressed()
        }
    }

    private fun createNote() {
        val title = binding.etTitle.text.toString().trim()
        val desc = binding.etDesc.text.toString().trim()

        createPresenter.create(noteId, title, desc, this)
    }

    override fun onBackPressed() {
        createNote()
        super.onBackPressed()
    }

    override fun onSuccess(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

    override fun onFailure(message: String) {
        TODO("Not yet implemented")
    }
}