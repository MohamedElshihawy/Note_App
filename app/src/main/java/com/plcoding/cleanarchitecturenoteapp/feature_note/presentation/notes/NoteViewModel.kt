package com.plcoding.cleanarchitecturenoteapp.feature_note.presentation.notes

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.plcoding.cleanarchitecturenoteapp.feature_note.domain.models.Note
import com.plcoding.cleanarchitecturenoteapp.feature_note.domain.use_case.UseCasesWrapper
import com.plcoding.cleanarchitecturenoteapp.feature_note.domain.utils.NoteEvent
import com.plcoding.cleanarchitecturenoteapp.feature_note.domain.utils.NoteOrder
import com.plcoding.cleanarchitecturenoteapp.feature_note.domain.utils.OrderType
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NoteViewModel @Inject constructor(
    private val useCasesWrapper: UseCasesWrapper,
) :
    ViewModel() {

    private val _state = mutableStateOf(NoteScreenState())
    val state: State<NoteScreenState> = _state

    private var lastDeletedNote: Note? = null

    private var lastNoteOrder: NoteOrder? = null

    // used to cancel operation of fetching note if we called it again while it didn't finish the last fetch operation
    private var getNotesJob: Job? = null

    init {
        getAllNotes(NoteOrder.Date(OrderType.Descending))
    }

    fun onEvent(noteEvent: NoteEvent) {
        when (noteEvent) {
            is NoteEvent.OrderNotes -> {
                if (state.value.noteOrder::class == noteEvent.noteOrder::class &&
                    state.value.noteOrder.orderType::class == noteEvent.noteOrder.orderType::class
                ) {
                    return
                }
                _state.value = state.value.copy(
                    noteOrder = noteEvent.noteOrder,
                )
                noteEvent.noteOrder
                getAllNotes(noteEvent.noteOrder)
                lastNoteOrder = noteEvent.noteOrder
            }

            is NoteEvent.DeleteNote -> {
                viewModelScope.launch {
                    useCasesWrapper.deleteNoteUseCase(noteEvent.note)
                    lastDeletedNote = noteEvent.note
                    getAllNotes(lastNoteOrder ?: NoteOrder.Date(OrderType.Descending))
                }
            }

            is NoteEvent.RestoreNote -> {
                viewModelScope.launch {
                    useCasesWrapper.addNoteUseCase(lastDeletedNote ?: return@launch)
                    lastDeletedNote = null
                    getAllNotes(lastNoteOrder ?: NoteOrder.Date(OrderType.Descending))
                }
            }

            is NoteEvent.ToggleOrderButton -> {
                _state.value = state.value.copy(
                    isOrderingButtonToggled = !state.value.isOrderingButtonToggled,
                )
            }

            is NoteEvent.OpenedNoteScreen -> {
                getAllNotes(lastNoteOrder ?: NoteOrder.Date(OrderType.Descending))
            }

            is NoteEvent.CopyNoteContent -> {
                _state.value = state.value.copy(
                    copiedContent = noteEvent.noteContent,
                )
            }
        }
    }

    private fun getAllNotes(noteOrder: NoteOrder) {
        getNotesJob?.cancel()

        getNotesJob = viewModelScope.launch {
            val result = useCasesWrapper.getNotesUseCase(noteOrder)

            result.collect {
                _state.value = state.value.copy(
                    allNotes = it,
                    noteOrder = noteOrder,
                )
            }
        }
    }
}
