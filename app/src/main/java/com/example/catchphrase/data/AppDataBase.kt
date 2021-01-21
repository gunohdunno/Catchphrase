package com.example.catchphrase.data

import android.content.Context
import androidx.room.*
import androidx.sqlite.db.SupportSQLiteDatabase
import com.example.catchphrase.viewmodel.Converter
import com.example.catchphrase.viewmodel.PhraseDao
import com.example.catchphrase.viewmodel.ProjectDao
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@Database(entities = [Project::class, Phrase::class], version = 1, exportSchema = false)
@TypeConverters(Converter::class)
abstract class AppDataBase : RoomDatabase() {
    abstract fun ProjectDao() : ProjectDao
    abstract fun PhraseDao() : PhraseDao

    private class ProjectDataBaseCallBack(
            private val scope: CoroutineScope
    ): RoomDatabase.Callback() {

        override fun onCreate(db: SupportSQLiteDatabase) {
            super.onCreate(db)
            INSTANCE?.let { database ->
                scope.launch {
                    populateDatabase(database.ProjectDao())
                }
            }
        }

        suspend fun populateDatabase(projectDao: ProjectDao) {
            // Delete all content here.
            projectDao.deleteAll()

            // Add sample words.
            val project = Project("Project 1", emptyList())
            projectDao.insert(project)
            val project1 = Project("Project 2", emptyList())
            projectDao.insert(project1)
        }
    }
    companion object {

        @Volatile
        private var INSTANCE: AppDataBase? = null

        fun getDatabase(
                context: Context,
                scope: CoroutineScope
        ): AppDataBase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                        context.applicationContext,
                        AppDataBase::class.java,
                        "app_database"
                )
                 .addCallback(ProjectDataBaseCallBack(scope))
                 .build()
                INSTANCE = instance
                instance
            }
        }
    }
}
