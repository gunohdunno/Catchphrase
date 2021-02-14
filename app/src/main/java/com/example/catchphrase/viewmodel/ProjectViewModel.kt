package com.example.catchphrase.viewmodel

import androidx.lifecycle.*
import com.example.catchphrase.data.Phrase
import com.example.catchphrase.data.Project
import com.example.catchphrase.data.ProjectRepository
import kotlinx.coroutines.launch

class ProjectViewModel(private val repository: ProjectRepository) : ViewModel() {
    val allProjects : LiveData<List<Project>> = repository.projectList.asLiveData()

    fun insert(project : Project) = viewModelScope.launch {
        repository.insert(project)
    }

    fun deleteAll() = viewModelScope.launch {
        repository.deleteAll()
    }

    fun getProject(id : Int) : LiveData<Project> {
        return repository.getProject(id)
    }
}

class ProjectViewModelFactory(private val repository: ProjectRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ProjectViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return ProjectViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}