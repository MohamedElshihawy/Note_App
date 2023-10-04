package com.plcoding.cleanarchitecturenoteapp.feature_note.presentation.add_edit_note

import android.net.Uri
import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.graphics.toArgb
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.plcoding.cleanarchitecturenoteapp.feature_note.domain.models.InvalidNoteException
import com.plcoding.cleanarchitecturenoteapp.feature_note.domain.models.Note
import com.plcoding.cleanarchitecturenoteapp.feature_note.domain.use_case.UseCasesWrapper
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AddEditNoteViewModel @Inject constructor(
    private val notUseCasesWrapper: UseCasesWrapper,
    savedStateHandle: SavedStateHandle,
) : ViewModel() {

    private val _noteTitle = mutableStateOf(
        NoteTextFieldState(
            hintText = "Enter Title",
        ),
    )
    val noteTitle: State<NoteTextFieldState> = _noteTitle

    private val _noteContent = mutableStateOf(
        NoteTextFieldState(
            hintText = "Enter Note Content",
        ),
    )
    val noteContent: State<NoteTextFieldState> = _noteContent

    private val _noteImageAttachment = mutableStateOf<Uri?>(null)
    val noteImageAttachment: State<Uri?> = _noteImageAttachment

    private val _noteAudioAttachment = mutableStateOf<Uri?>(null)
    val noteAudioAttachment: State<Uri?> = _noteAudioAttachment

    private val _noteColor = mutableStateOf(Note.notesColors.random().toArgb())
    val noteColor: State<Int> = _noteColor

    private val _eventFlow = MutableSharedFlow<UiEvents>()
    val eventFlow = _eventFlow.asSharedFlow()
    var job: Job? = null

    private var currentNoteId: Int? = null

    init {
        // newNote = savedStateHandle.get<Boolean>("updateNote")
        savedStateHandle.get<Int>("noteId")?.let { noteId ->
            if (noteId != -1) {
                job = viewModelScope.launch {
                    notUseCasesWrapper.getNoteUseCase(noteId)?.also { note ->
                        currentNoteId = note.id
                        _noteColor.value = note.color
                        _noteContent.value = noteContent.value.copy(
                            text = note.content,
                            isHintVisible = false,
                        )
                        _noteTitle.value = noteTitle.value.copy(
                            text = note.title,
                            isHintVisible = false,
                        )
                        _noteImageAttachment.value = Uri.parse(note.imageAttachment)
                        _noteAudioAttachment.value = Uri.parse(note.audioAttachment)
                    }
                }
            }
        }
    }

    fun onEvent(event: AddEditNoteEvent) {
        when (event) {
            is AddEditNoteEvent.TitleEntered -> {
                _noteTitle.value = noteTitle.value.copy(
                    text = event.title,
                )
            }

            is AddEditNoteEvent.ChangeTitleFocus -> {
                _noteTitle.value = noteTitle.value.copy(
                    isHintVisible = !event.focusState.isFocused &&
                        noteTitle.value.text.isBlank(),
                )
            }

            is AddEditNoteEvent.ContentEntered -> {
                _noteContent.value = noteContent.value.copy(
                    text = event.content,
                )
            }

            is AddEditNoteEvent.ChangeContentFocus -> {
                _noteContent.value = noteContent.value.copy(
                    isHintVisible = !event.focusState.isFocused &&
                        noteContent.value.text.isBlank(),
                )
            }

            is AddEditNoteEvent.ChangeColor -> {
                _noteColor.value = event.color
            }

            is AddEditNoteEvent.SaveNote -> {
                Log.e("TAG", "onEvent: save ")
                viewModelScope.launch {
                    val note = Note(
                        title = noteTitle.value.text,
                        content = noteContent.value.text,
                        color = noteColor.value,
                        timeStamp = System.currentTimeMillis(),
                        id = currentNoteId,
                        imageAttachment = noteImageAttachment.value.toString(),
                        audioAttachment = noteAudioAttachment.value.toString(),
                    )
                    try {
                        notUseCasesWrapper.addNoteUseCase(note)
                        _eventFlow.emit(
                            UiEvents.ShowSnackBar(
                                message = "Saved Your Note",
                            ),
                        )
                        _eventFlow.emit(UiEvents.SaveNote)
                    } catch (e: InvalidNoteException) {
                        _eventFlow.emit(
                            UiEvents.ShowSnackBar(
                                message = e.message ?: "Couldn't Save Your Note",
                            ),
                        )
                    }
                }
            }

            is AddEditNoteEvent.PickImage -> {
                _noteImageAttachment.value = event.uri
            }

            is AddEditNoteEvent.RecordAudio -> {
                // AudioRecorderImp().
            }
        }
    }

    sealed class UiEvents {
        class ShowSnackBar(val message: String) : UiEvents()
        object SaveNote : UiEvents()
    }
}
