package com.co.routine.view.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.co.routine.MainApp
import com.co.routine.R
import com.co.routine.databinding.ItemRvBinding
import com.co.routine.service.model.Project

class ProjectListAdapter(mainApp: MainApp) :
    RecyclerView.Adapter<ProjectListAdapter.ProjectListViewHolder>() {

    private val projectList = ArrayList<Project>()
    private val inflater = LayoutInflater.from(mainApp)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProjectListViewHolder {
        val viewDataBinding =
            DataBindingUtil.inflate<ItemRvBinding>(inflater, R.layout.item_rv, parent, false)
        return ProjectListViewHolder(viewDataBinding)
    }

    fun setProjectList(projectList: List<Project>?) {
        projectList?.let {
            val size = itemCount
            this.projectList.clear()
            notifyItemRangeRemoved(0, size)
            this.projectList.addAll(it)
            notifyItemRangeInserted(0, itemCount)
        }
    }

    override fun getItemCount(): Int {
        return projectList.size
    }

    override fun onBindViewHolder(holder: ProjectListViewHolder, position: Int) {
        holder.viewDataBinding.project = projectList[position]
        holder.viewDataBinding.executePendingBindings()
    }

    class ProjectListViewHolder(val viewDataBinding: ItemRvBinding) :
        RecyclerView.ViewHolder(viewDataBinding.root)
}