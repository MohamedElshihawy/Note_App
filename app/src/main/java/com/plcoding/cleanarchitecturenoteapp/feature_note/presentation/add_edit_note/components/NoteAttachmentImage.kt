package com.plcoding.cleanarchitecturenoteapp.feature_note.presentation.add_edit_note.components

import android.net.Uri
import androidx.compose.foundation.Image
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImagePainter
import coil.compose.rememberAsyncImagePainter

@Composable
fun NoteImageAttachment(uri: Uri) {
    val painter = rememberAsyncImagePainter(uri)

    Image(
        painter = painter,
        contentDescription = "Image",
        modifier = Modifier
            .clip(RoundedCornerShape(24.dp)),

    )

    if (painter.state is AsyncImagePainter.State.Error) {
        Text(
            text = "Error loading image",
            color = Color.Red,
        )
    }
}
