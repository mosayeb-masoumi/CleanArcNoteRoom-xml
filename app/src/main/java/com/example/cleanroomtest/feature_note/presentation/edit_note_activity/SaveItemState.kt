package com.example.cleanroomtest.feature_note.presentation.edit_note_activity

import com.example.cleanroomtest.feature_note.domain.model.Note

data class SaveItemState(

    val isLoading: Boolean = false,
//    val memesList: List<Meme> = emptyList(),
    val data: Note? = null,
    val error : String = ""
)
