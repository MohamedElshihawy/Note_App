package com.plcoding.cleanarchitecturenoteapp.feature_note.presentation.add_edit_note.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.FocusState
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp

@Composable
fun TransparentHintTextFiled(
    modifier: Modifier = Modifier,
    text: String,
    hint: String,
    isHintVisible: Boolean = true,
    textStyle: TextStyle = TextStyle(),
    onValueChanged: (String) -> Unit,
    onFocusChange: (FocusState) -> Unit,
    singleLine: Boolean = false,
) {
    Surface(
        elevation = 1.dp,
        modifier = modifier
            .heightIn(
                min = 40.dp,
            )
            .clip(shape = RoundedCornerShape(12.dp)),

    ) {
        Box(
            contentAlignment = Alignment.CenterStart,
        ) {
            BasicTextField(
                value = text,
                textStyle = textStyle,
                singleLine = singleLine,
                onValueChange = onValueChanged,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp)
                    .onFocusChanged {
                        onFocusChange(it)
                    },
            )

            if (isHintVisible) {
                Text(
                    text = hint,
                    style = textStyle,
                    color = MaterialTheme.colors.onSurface,
                    modifier = Modifier.padding(8.dp),
                )
            }
        }
    }
}
