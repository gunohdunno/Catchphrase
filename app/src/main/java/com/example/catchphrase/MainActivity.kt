package com.example.catchphrase

import android.Manifest
import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.widget.Button
import android.widget.Toast
import androidx.activity.viewModels
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.catchphrase.data.Phrase
import com.example.catchphrase.data.Project
import com.example.catchphrase.viewmodel.ProjectViewModel
import com.example.catchphrase.viewmodel.ProjectViewModelFactory
import com.google.android.material.floatingactionbutton.FloatingActionButton

class MainActivity : AppCompatActivity(), ProjectAdapter.CellClickListener {
    private val PERMISSIONSREQ = 1
    private var phraseManager : PhraseManager? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        // Custom Action Bar
        setSupportActionBar(findViewById(R.id.appToolBar))

        // Recycler view adapter
        val recyclerView = findViewById<RecyclerView>(R.id.projectList)
        val adapter = ProjectAdapter(this)
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(this)

        // Populate list of projects
        projectViewModel.allProjects.observe(this, { projects ->
            projects?.let{ adapter.submitList(it)}
        })

        // Record button
        val recordButton : FloatingActionButton = findViewById(R.id.record_fab)
        phraseManager = PhraseManager(applicationContext)
        recordButton.setOnClickListener {
            requestPermissions()
            val phrase = Phrase("", -1)
            if (!phraseManager!!.isRecording) {
                phraseManager!!.startRecording(phrase)
                Toast.makeText(applicationContext, "Recording has started", Toast.LENGTH_SHORT).show()
            } else {
                phraseManager!!.stopRecording()
                val newProject = Project("New Project", emptyList())
                phrase.projectId = newProject.id
                val phraseList : List<Phrase> = mutableListOf(phrase)
                newProject.phrases  = phraseList
                projectViewModel.insert(newProject)
                Toast.makeText(applicationContext, "Recording has stopped", Toast.LENGTH_SHORT).show()
            }
        }
    }

    override fun onCellClickListener(currProject: Project) {
        Toast.makeText(applicationContext, "Cell has been clicked", Toast.LENGTH_SHORT).show()
        if (!phraseManager!!.isPlaying) {
            projectViewModel.getProject(currProject.id).observe(this, {
                val projectPhrases : List<Phrase> = it.phrases
                if (projectPhrases.isNotEmpty()) {
                    phraseManager!!.startPlaying(projectPhrases[0].id)
                }
            })
            Toast.makeText(applicationContext, "Playing has started", Toast.LENGTH_SHORT).show()

        }else {
            phraseManager!!.stopPlaying()
            Toast.makeText(applicationContext, "Playing has stopped", Toast.LENGTH_SHORT).show()
        }
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

    private fun requestPermissions(){
        val permissionsRequired = mutableListOf<String>()

        val hasRecordPermission = ContextCompat.checkSelfPermission(this, Manifest.permission.RECORD_AUDIO) == PackageManager.PERMISSION_GRANTED
        if (!hasRecordPermission){
            permissionsRequired.add(Manifest.permission.RECORD_AUDIO)
        }

        val hasStoragePermission = ContextCompat.checkSelfPermission(this,Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED
        if (!hasStoragePermission){
            permissionsRequired.add(Manifest.permission.WRITE_EXTERNAL_STORAGE)
        }

        if (permissionsRequired.isNotEmpty()){
            ActivityCompat.requestPermissions(this, permissionsRequired.toTypedArray(),PERMISSIONSREQ)
        }
    }
}

