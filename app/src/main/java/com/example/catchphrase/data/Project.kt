package com.example.catchphrase.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName="project_table")
data class Project(
    @PrimaryKey(autoGenerate = true) val id: Long,
    @ColumnInfo(name="projectName") val name: String,
    @ColumnInfo(name="phrases") val phrase: List<Long>
)
