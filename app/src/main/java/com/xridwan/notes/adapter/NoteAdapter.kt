package com.xridwan.notes.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import androidx.recyclerview.widget.RecyclerView
import com.amulyakhare.textdrawable.util.ColorGenerator
import com.xridwan.notes.databinding.NotedItemLayoutBinding
import com.xridwan.notes.model.Note

class NoteAdapter(private val context: Context, private val noteList: List<Note>) :
    RecyclerView.Adapter<NoteAdapter.NoteViewHolder>() {

    private lateinit var onItemClickCallBack: OnItemClickCallBack

    fun setOnItemCLickCallBack(onItemClickCallBack: OnItemClickCallBack) {
        this.onItemClickCallBack = onItemClickCallBack
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NoteViewHolder {
        val binding =
            NotedItemLayoutBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return NoteViewHolder(binding)
    }

    override fun onBindViewHolder(holder: NoteViewHolder, position: Int) {
        val animation = AnimationUtils.loadAnimation(context, android.R.anim.slide_in_left)

        holder.itemView.animation = animation
        holder.bind(noteList[position])
    }

    override fun getItemCount(): Int = noteList.size

    inner class NoteViewHolder(private val binding: NotedItemLayoutBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(note: Note) {
            with(binding) {
                tvTitle.text = note.title
                tvDesc.text = note.desc
                tvDate.text = note.date

                val generator = ColorGenerator.MATERIAL //custom color
                val color = generator.randomColor
                cardBg.setCardBackgroundColor(color)

                itemView.setOnClickListener {
                    onItemClickCallBack.onItemClicked(note)
                }

                itemView.setOnLongClickListener {
                    onItemClickCallBack.onItemLongClicked(note)
                    return@setOnLongClickListener true
                }
            }
        }
    }

    interface OnItemClickCallBack {
        fun onItemClicked(note: Note)
        fun onItemLongClicked(note: Note)
    }
}