package com.co.routine.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.co.routine.service.repository.GitHubClient

class MainListViewModel(application: Application) : AndroidViewModel(application) {

    val projectListObs = GitHubClient.getProjectList("google")
}