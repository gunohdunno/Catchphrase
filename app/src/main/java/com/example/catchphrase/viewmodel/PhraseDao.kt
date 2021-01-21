package com.example.catchphrase.viewmodel

import androidx.room.*
import com.example.catchphrase.data.Phrase
import com.example.catchphrase.data.Project

@Dao
interface PhraseDao {
    @Query("SELECT * FROM phrase_table WHERE projectId = :projectId")
    fun getProjectPhrases(projectId: Long): List<Phrase>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertProject(project: Project)

    @Delete
    suspend fun deleteProject(project: Project)
}
