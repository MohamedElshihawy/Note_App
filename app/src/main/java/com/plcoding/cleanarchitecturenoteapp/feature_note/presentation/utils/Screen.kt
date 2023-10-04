package com.plcoding.cleanarchitecturenoteapp.feature_note.presentation.utils

sealed class Screen(val route: String) {
    object AllNotesScreen : Screen("all_notes_screen")
    object AddEditNotes : Screen("add_edit_notes")
}
