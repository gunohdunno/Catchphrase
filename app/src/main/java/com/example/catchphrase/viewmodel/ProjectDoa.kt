package com.example.catchphrase.viewmodel

import androidx.room.*
import com.example.catchphrase.data.Project

@Dao
interface ProjectDoa {
    @Query("SELECT * FROM project_table")
    fun getAllProjects(): List<Project>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(project: Project)

    @Delete
    fun deleteProject(project: Project)
}