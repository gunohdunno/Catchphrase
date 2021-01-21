package com.example.catchphrase

import android.app.Application
import com.example.catchphrase.data.AppDataBase
import com.example.catchphrase.data.ProjectRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob

class ProjectApplication : Application() {
    // No need to cancel this scope as it'll be torn down with the process
    val applicationScope = CoroutineScope(SupervisorJob())

    // Using by lazy so the database and the repository are only created when they're needed
    // rather than when the application starts
    val database by lazy { AppDataBase.getDatabase(this, applicationScope) }
    val repository by lazy { ProjectRepository(database.ProjectDao(), database.PhraseDao()) }

}