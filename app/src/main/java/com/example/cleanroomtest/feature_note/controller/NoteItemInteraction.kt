package com.example.cleanroomtest.feature_note.controller

import com.example.cleanroomtest.feature_note.domain.model.Note

interface NoteItemInteraction {

    fun noteItemOnclick(note: Note)
    fun deleteItemOnclick(note: Note)
}