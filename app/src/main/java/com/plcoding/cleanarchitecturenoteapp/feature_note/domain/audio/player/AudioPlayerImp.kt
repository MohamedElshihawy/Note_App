package com.plcoding.cleanarchitecturenoteapp.feature_note.domain.audio.player

import android.content.Context
import android.media.MediaPlayer
import androidx.core.net.toUri
import java.io.File

class AudioPlayerImp(
    private val context: Context,
) : AudioPlayer {

    private var audioPlayer: MediaPlayer? = null

    override fun startPlayingAudio(file: File) {
        MediaPlayer.create(context, file.toUri()).apply {
            audioPlayer = this
            start()
        }
    }

    override fun stopPlayingAudio() {
        audioPlayer?.stop()
        audioPlayer?.release()
        audioPlayer?.reset()
    }
}
