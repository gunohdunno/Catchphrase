package com.example.catchphrase.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName="project_table")
data class Project(
    @ColumnInfo(name="projectName") var name: String,
    @ColumnInfo(name="phrases") var phrases: List<Phrase>
    ) {
    @PrimaryKey(autoGenerate = true) var id: Int = 0
}
