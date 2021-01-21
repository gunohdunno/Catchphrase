package com.example.catchphrase.viewmodel

import androidx.room.*
import com.example.catchphrase.data.Project
import kotlinx.coroutines.flow.Flow

@Dao
interface ProjectDao {
    @Query("SELECT * FROM project_table")
    fun getAllProjects(): Flow<List<Project>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(project: Project)

    @Delete
    fun deleteProject(project: Project)

    @Query("DELETE FROM project_table")
    suspend fun deleteAll()
}