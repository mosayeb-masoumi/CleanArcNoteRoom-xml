package com.example.cleanroomtest.feature_note.domain.use_case

import com.example.cleanroomtest.common.Resource
import com.example.cleanroomtest.feature_note.domain.model.Note
import com.example.cleanroomtest.feature_note.domain.repository.NoteRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import java.io.IOException


class DeleteNoteUseCase(private val repository: NoteRepository){

//    suspend operator fun invoke(note: Note) {
//        repository.deleteNote(note)
//    }

    operator fun invoke(note: Note): Flow<Resource<Note?>> = flow {

        try {
            repository.deleteNote(note)
            emit(Resource.Success<Note?>(note))
        }catch (e: IOException){
            emit(Resource.Error<Note?>(e.localizedMessage ?: "check your internet connection", null))
        }
    }


}