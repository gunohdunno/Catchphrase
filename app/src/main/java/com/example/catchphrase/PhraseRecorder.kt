package com.example.catchphrase

import android.media.MediaRecorder
import android.widget.Button

class PhraseRecorder {
    private var fileName : String? = null
    private var recordButton : Button? = null
    private var recorder : MediaRecorder? = null

    fun toggleRecord(recorder : MediaRecorder) : Boolean {

        return true
    }
}