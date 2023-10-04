package com.plcoding.cleanarchitecturenoteapp.feature_note.domain.use_case

data class UseCasesWrapper(
    val getNotesUseCase: GetNotesUseCase,
    val deleteNoteUseCase: DeleteNoteUseCase,
    val addNoteUseCase: AddNoteUseCase,
    val getNoteUseCase: GetNoteUseCase,
    val updateNote: UpdateNoteUseCase,
)
