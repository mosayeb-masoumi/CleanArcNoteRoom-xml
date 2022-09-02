package com.example.cleanroomtest.feature_note.domain.use_case

data class NoteUseCases(
    val addNoteUseCase: AddNoteUseCase,
    val clearDbUseCase: ClearDbUseCase,
    val deleteNoteUseCase: DeleteNoteUseCase,
    val getNoteUseCase: GetNoteUseCase,
    val getNotesUseCase: GetNotesUseCase
)