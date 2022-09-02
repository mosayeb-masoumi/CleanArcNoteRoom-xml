package com.example.cleanroomtest.feature_note.domain.use_case

import com.example.cleanroomtest.feature_note.domain.model.Note
import com.example.cleanroomtest.feature_note.domain.repository.NoteRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetNotesUseCase (private val repository: NoteRepository) {

    // because we used flow in getlist in dao then suspend is redundant

    operator fun invoke(): Flow<List<Note>> {
        val noteList = repository.getNotes()
        return noteList
    }
}