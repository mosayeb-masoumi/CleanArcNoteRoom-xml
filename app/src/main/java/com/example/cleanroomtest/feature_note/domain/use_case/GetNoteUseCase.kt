package com.example.cleanroomtest.feature_note.domain.use_case

import com.example.cleanroomtest.common.Resource
import com.example.cleanroomtest.feature_note.domain.model.Note
import com.example.cleanroomtest.feature_note.domain.repository.NoteRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import java.io.IOException


class GetNoteUseCase (private val repository: NoteRepository) {


   operator fun invoke(id: Int): Flow<Resource<Note?>> = flow {

        try {
            emit(Resource.Loading<Note?>())
            val note = repository.getNoteById(id)
            emit(Resource.Success<Note?>(note))

        }catch (e: IOException){
            emit(Resource.Error<Note?>(e.localizedMessage ?: "check your internet connection", null))
        }
    }

}