package com.example.cleanroomtest.feature_note.presentation.notes_activity

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.cleanroomtest.common.Resource
import com.example.cleanroomtest.feature_note.domain.model.Note
import com.example.cleanroomtest.feature_note.domain.use_case.NoteUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(private val useCases: NoteUseCases): ViewModel() {


    private val _getNotesResponse = MutableLiveData<List<Note>>()
    val getNotesLivedata: LiveData<List<Note>> get() = _getNotesResponse

    private val _deleteNotesResponse = MutableLiveData<DeleteNoteState>()
    val deleteNotesLivedata: LiveData<DeleteNoteState> get() = _deleteNotesResponse



    fun getNoteList(){

        useCases.getNotesUseCase().onEach { notes ->
            _getNotesResponse.value = notes
        }.launchIn(viewModelScope)

    }


    fun deleteNoteItem(note: Note){

        useCases.deleteNoteUseCase(note).onEach { result ->
            when (result) {

                is Resource.Loading -> {
                    _deleteNotesResponse.value = DeleteNoteState(isLoading = true)
                }
                is Resource.Success -> {
                    _deleteNotesResponse.value = DeleteNoteState(data = result.data)
                }
                is Resource.Error -> {
                    _deleteNotesResponse.value = DeleteNoteState(error = "An unexpected error occured")
                }

            }
        }.launchIn(viewModelScope)

    }

}