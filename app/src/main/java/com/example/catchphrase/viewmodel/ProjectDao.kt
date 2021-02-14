package com.example.catchphrase.viewmodel

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.catchphrase.data.Phrase
import com.example.catchphrase.data.Project
import kotlinx.coroutines.flow.Flow

@Dao
interface ProjectDao {
    @Query("SELECT * FROM project_table")
    fun getAllProjects(): Flow<List<Project>>

    @Query("SELECT * FROM project_table WHERE id = :id")
    fun getProject(id : Int): LiveData<Project>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(project: Project)

    @Delete
    fun deleteProject(project: Project)

    @Query("DELETE FROM project_table")
    suspend fun deleteAll()
}