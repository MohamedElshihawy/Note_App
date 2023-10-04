package com.plcoding.cleanarchitecturenoteapp.feature_note.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.ui.Modifier
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.plcoding.cleanarchitecturenoteapp.feature_note.presentation.add_edit_note.AddEditScreen
import com.plcoding.cleanarchitecturenoteapp.feature_note.presentation.notes.NoteScreen
import com.plcoding.cleanarchitecturenoteapp.feature_note.presentation.utils.Screen
import com.plcoding.cleanarchitecturenoteapp.ui.theme.CleanArchitectureNoteAppTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            CleanArchitectureNoteAppTheme(darkTheme = isSystemInDarkTheme()) {
                Surface(
                    color = MaterialTheme.colors.background,
                    modifier = Modifier.fillMaxSize(),
                ) {
                    val navController = rememberNavController()
                    NavHost(
                        navController = navController,
                        startDestination = Screen.AllNotesScreen.route,
                    ) {
                        composable(route = Screen.AllNotesScreen.route) {
                            NoteScreen(navController)
                        }
                        composable(
                            route = Screen.AddEditNotes.route +
                                "?noteId={noteId}&noteColor={coteColor}&updateNote={updateNote}",
                            arguments = listOf(
                                navArgument(
                                    name = "noteId",
                                ) {
                                    type = NavType.IntType
                                    defaultValue = -1
                                },
                                navArgument(
                                    name = "noteColor",
                                ) {
                                    type = NavType.IntType
                                    defaultValue = -1
                                },
                                navArgument("updateNote") {
                                    type = NavType.BoolType
                                    defaultValue = false
                                },
                            ),
                        ) { navBackStack ->
                            val color = navBackStack.arguments?.getInt("noteColor") ?: -1
                            AddEditScreen(
                                navController = navController,
                                noteColor = color,
                            )
                        }
                    }
                }
            }
        }
    }
}
