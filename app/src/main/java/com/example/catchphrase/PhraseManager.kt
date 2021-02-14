package com.example.catchphrase

import android.content.Context
import android.content.pm.PackageManager
import android.media.MediaCas
import android.media.MediaExtractor
import android.media.MediaPlayer
import android.media.MediaRecorder
import android.widget.Button
import android.widget.Toast
import com.example.catchphrase.data.Phrase
import java.io.File

class PhraseManager(private val ctx : Context) {
    private var recorder : MediaRecorder? = null
    private var player : MediaPlayer? = null
    var isRecording : Boolean = false
    var isPlaying : Boolean = false

    private fun filePathForId(id : Int) : String {
        return ctx.getExternalFilesDir(null)!!.absolutePath + "/$id.aac"
    }

    fun stopRecording(){
        isRecording = !isRecording
        recorder?.stop()
        recorder?.release()
        recorder = null
    }

    fun startRecording(phrase : Phrase) : Boolean {
        if (ctx.packageManager.hasSystemFeature(PackageManager.FEATURE_MICROPHONE)) {
            isRecording = !isRecording
            // initialize recorder
            recorder = MediaRecorder()
            recorder?.setAudioSource(MediaRecorder.AudioSource.MIC)
            // set encoder and format
            recorder?.setOutputFormat(MediaRecorder.OutputFormat.AAC_ADTS)
            recorder?.setAudioEncoder(MediaRecorder.AudioEncoder.AAC)
            // sample rate and bit rate (kHz)
            recorder?.setAudioSamplingRate(48000)
            recorder?.setAudioEncodingBitRate(128000)
            // set phrase data
            val fileLocation : String = filePathForId(phrase.id)
            phrase.filePath = fileLocation
            recorder?.setOutputFile(fileLocation)
            // start recording
            recorder?.prepare()
            recorder?.start()
            return true
        } else {
            return false
        }
    }

    fun stopPlaying() {
        player?.stop()
        player?.release()
        player = null
        this.isPlaying = !this.isPlaying
    }

    fun startPlaying(id : Int) : Boolean {
        val filePath = filePathForId(id)
        if (File(filePath).exists()) {
            player = MediaPlayer()
            player?.setDataSource(filePath)
            player?.prepare()
            player?.start()
            this.isPlaying = !this.isPlaying
            return true
        }else {
            return false
        }
    }
}