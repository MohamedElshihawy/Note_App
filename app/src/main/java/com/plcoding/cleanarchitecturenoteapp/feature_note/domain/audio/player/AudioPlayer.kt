package com.plcoding.cleanarchitecturenoteapp.feature_note.domain.audio.player

import java.io.File

interface AudioPlayer {

    fun startPlayingAudio(file: File)

    fun stopPlayingAudio()
}
