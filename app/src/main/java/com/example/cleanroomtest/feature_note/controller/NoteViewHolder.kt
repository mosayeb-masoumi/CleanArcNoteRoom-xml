
package com.example.cleanroomtest.feature_note.controller

import androidx.recyclerview.widget.RecyclerView
import com.example.cleanroomtest.databinding.RowBinding
import com.example.cleanroomtest.feature_note.domain.model.Note


class NoteViewHolder (private val binding: RowBinding) : RecyclerView.ViewHolder(binding.root)  {


    fun bind(item: Note) {

        binding.txtTitle.text = item.title
        binding.txtDescription.text = item.content
    }

    fun setOnListHolderListener(listener: NoteItemInteraction?, model: Note?) {

        binding.root.setOnClickListener {
            model?.let { listener?.noteItemOnclick(it) }
        }

        binding.imgDelete.setOnClickListener {
            model?.let { listener?.deleteItemOnclick(it) }
        }

    }
}