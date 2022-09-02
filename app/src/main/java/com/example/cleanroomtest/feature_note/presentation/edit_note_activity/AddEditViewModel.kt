package com.example.cleanroomtest.feature_note.presentation.edit_note_activity

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
class AddEditViewModel @Inject constructor(private val usesCases: NoteUseCases) : ViewModel()  {


    private val _getItemResponse = MutableLiveData<GetItemDataState>()
    val getItemLivedata: LiveData<GetItemDataState> get() = _getItemResponse


    private val _saveItemResponse = MutableLiveData<SaveItemState>()
    val saveItemLivedata: LiveData<SaveItemState> get() = _saveItemResponse


    fun getNoteItem(id: Int) {

        usesCases.getNoteUseCase(id).onEach { result ->
            when (result) {

                is Resource.Loading -> {
                    _getItemResponse.value = GetItemDataState(isLoading = true)
                }
                is Resource.Success -> {
                    _getItemResponse.value = GetItemDataState(data = result.data)
                }
                is Resource.Error -> {
                    _getItemResponse.value = GetItemDataState(error = "An unexpected error occured")
                }

            }
        }.launchIn(viewModelScope)

    }


    fun addEditNoteItem(note: Note){

        usesCases.addNoteUseCase(note).onEach { result ->
            when (result) {

                is Resource.Loading -> {
                    _saveItemResponse.value = SaveItemState(isLoading = true)
                }
                is Resource.Success -> {
                    _saveItemResponse.value = SaveItemState(data = result.data)
                }
                is Resource.Error -> {
                    _saveItemResponse.value = SaveItemState(error = "An unexpected error occured")
                }

            }
        }.launchIn(viewModelScope)
    }


}