package com.plcoding.cleanarchitecturenoteapp.feature_note.presentation.add_edit_note

import android.net.Uri
import androidx.compose.ui.focus.FocusState

sealed class AddEditNoteEvent {

    data class TitleEntered(val title: String) : AddEditNoteEvent()
    data class ChangeTitleFocus(val focusState: FocusState) : AddEditNoteEvent()
    data class ContentEntered(val content: String) : AddEditNoteEvent()
    data class ChangeContentFocus(val focusState: FocusState) : AddEditNoteEvent()
    data class ChangeColor(val color: Int) : AddEditNoteEvent()
    data class PickImage(val uri: Uri?) : AddEditNoteEvent()
    object RecordAudio : AddEditNoteEvent()
    object SaveNote : AddEditNoteEvent()
}
