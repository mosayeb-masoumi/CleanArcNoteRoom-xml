package com.example.cleanroomtest.feature_note.presentation.notes_activity

import com.example.cleanroomtest.feature_note.domain.model.Note

data class DeleteNoteState(
    val isLoading: Boolean = false,
    val data: Note? = null,
    val error: String = ""
)


