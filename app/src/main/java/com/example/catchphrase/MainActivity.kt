package com.example.catchphrase

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import androidx.activity.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.catchphrase.data.Project
import com.example.catchphrase.viewmodel.ProjectViewModel
import com.example.catchphrase.viewmodel.ProjectViewModelFactory

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        // Custom Action Bar
        setSupportActionBar(findViewById(R.id.appToolBar))

        // Recycler view adapter
        val recyclerView = findViewById<RecyclerView>(R.id.projectList)
        val adapter = ProjectAdapter()
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(this)

        // Populate list of projects

        projectViewModel.allProjects.observe(this, Observer { projects ->
            projects?.let{ adapter.submitList(it)}
        })
    }

    private val projectViewModel: ProjectViewModel by viewModels {
        ProjectViewModelFactory((application as ProjectApplication).repository)
    }

    override fun onOptionsItemSelected(item: MenuItem) = when (item.itemId) {
        R.id.action_settings -> {
            // User chose the "Settings" action
            projectViewModel.deleteAll()
            true
        }
        R.id.action_add -> {
            // User chose the "Add" action
            val project = Project("Project Example", emptyList())
            projectViewModel.insert(project)
            true
        }
        else -> {
            // If we got here, the user's action was not recognized.
            // Invoke the superclass to handle it.
            super.onOptionsItemSelected(item)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater: MenuInflater = menuInflater
        inflater.inflate(R.menu.toolbar_menu, menu)
        return true
    }
}