package com.plcoding.cleanarchitecturenoteapp.feature_note.domain.utils

import com.plcoding.cleanarchitecturenoteapp.feature_note.domain.models.Note

// a class used to denote every possible action for a certain screen
sealed class NoteEvent {

    data class OrderNotes(val noteOrder: NoteOrder) : NoteEvent()

    data class DeleteNote(val note: Note) : NoteEvent()

    data class CopyNoteContent(val noteContent: String) : NoteEvent()

    object RestoreNote : NoteEvent()

    object ToggleOrderButton : NoteEvent()

    object OpenedNoteScreen : NoteEvent()
}
