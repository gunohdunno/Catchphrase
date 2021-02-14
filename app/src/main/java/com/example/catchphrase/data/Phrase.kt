package com.example.catchphrase.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "phrase_table")
data class Phrase(
    @ColumnInfo(name = "phraseData") var filePath: String,
    @ColumnInfo(name = "projectId") var projectId: Int
    ) {
    @PrimaryKey(autoGenerate = true) var id: Int = 0
}
