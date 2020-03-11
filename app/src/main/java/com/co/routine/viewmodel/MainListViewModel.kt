package com.co.routine.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.co.routine.service.model.Project
import com.co.routine.service.repository.GitHubClient
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async


class MainListViewModel(application: Application) : AndroidViewModel(application) {

    suspend fun getProjectList() : LiveData<List<Project>> {

        val projectListObs = viewModelScope.async(Dispatchers.IO) {
            GitHubClient.getProjectList("google")
        }
        return projectListObs.await()
    }
}
