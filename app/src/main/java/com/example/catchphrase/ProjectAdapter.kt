package com.example.catchphrase

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.catchphrase.data.Project

class ProjectAdapter : ListAdapter<Project, ProjectAdapter.ProjectViewHolder>(ProjectComparator()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProjectViewHolder {
        return ProjectViewHolder.create(parent)
    }

    override fun onBindViewHolder(holder: ProjectViewHolder, position: Int) {
        val current = getItem(position)
        holder.bind(current.id, current.name)
    }

    class ProjectViewHolder(view : View) : RecyclerView.ViewHolder(view) {
        private val projectName: TextView = view.findViewById(R.id.projectName)
        private val projectId : TextView = view.findViewById(R.id.projectId)
        fun bind(id : Int, name : String?) {
            projectId.text = id.toString()
            projectName.text = name
        }
        companion object {
            fun create(parent: ViewGroup): ProjectViewHolder {
                val view: View = LayoutInflater.from(parent.context)
                        .inflate(R.layout.project_adapter_item_layout, parent, false)
                return ProjectViewHolder(view)
            }
        }
    }
    class ProjectComparator : DiffUtil.ItemCallback<Project>() {
        override fun areItemsTheSame(oldItem: Project, newItem: Project): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Project, newItem: Project): Boolean {
            return oldItem.phrases.containsAll(newItem.phrases)
        }
    }

}