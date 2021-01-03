package com.example.catchphrase.viewmodel

import androidx.room.Dao
import androidx.room.Query
import com.example.catchphrase.data.Phrase

@Dao
interface PhraseDoa {
    @Query("SELECT * FROM phrase_table WHERE projectId = :projectId")
    fun getProjectPhrases(projectId: Long): List<Phrase>
}
