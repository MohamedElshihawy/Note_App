package com.plcoding.cleanarchitecturenoteapp.feature_note.domain.audio.recorder

import java.io.File

interface AudioRecorder {

    fun startRecordingAudio(file: File)

    fun stopRecording()
}
