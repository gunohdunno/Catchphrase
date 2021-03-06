package com.example.catchphrase.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "phrase_table")
data class Phrase(
    @PrimaryKey(autoGenerate = true) val id: Long,
    @ColumnInfo(name = "phraseData") val filePath: String,
    @ColumnInfo(name = "projectId") val projectId: Long
    ) {
    fun RecordAudio() {

    }

    fun PlayBackAudio() {

    }
}
