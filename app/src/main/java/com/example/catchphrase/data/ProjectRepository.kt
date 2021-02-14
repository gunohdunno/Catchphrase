package com.example.catchphrase.data

import androidx.annotation.WorkerThread
import androidx.lifecycle.LiveData
import com.example.catchphrase.viewmodel.PhraseDao
import com.example.catchphrase.viewmodel.ProjectDao
import kotlinx.coroutines.flow.Flow

class ProjectRepository(private val projectDao: ProjectDao, private val phraseDao: PhraseDao) {
    val projectList : Flow<List<Project>> = projectDao.getAllProjects()

    // By default Room runs suspend queries off the main thread, therefore, we don't need to
    // implement anything else to ensure we're not doing long running database work
    // off the main thread.
    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun insert(project : Project) {
        projectDao.insert(project)
    }

    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    fun getProject(id : Int) : LiveData<Project> {
        return projectDao.getProject(id)
    }

    @WorkerThread
    suspend fun deleteAll() {
        projectDao.deleteAll()
    }


}