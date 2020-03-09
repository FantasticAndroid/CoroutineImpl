package com.co.routine.service.repository

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.co.routine.service.model.Project
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object GitHubClient {
    private var gitHubService: GitHubService
    private val TAG = GitHubClient::class.java.simpleName

    init {
        val retrofit = Retrofit.Builder()
            .baseUrl(GitHubService.HTTPS_API_GITHUB_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        gitHubService = retrofit.create(GitHubService::class.java)
    }

    suspend fun getProjectList(userId: String): LiveData<List<Project>> {

        val projectListObs = MutableLiveData<List<Project>>()

        val projectList = gitHubService.getProjectList(userId)
        projectListObs.postValue(projectList)

        /*val call = gitHubService.getProjectList(userId)
        call.enqueue(object : Callback<List<Project>> {
            override fun onFailure(call: Call<List<Project>>, t: Throwable) {
                Log.e(TAG, t.message + "onFailure")
            }

            override fun onResponse(
                call: Call<List<Project>>, response:
                Response<List<Project>>
            ) {
                Log.d(TAG,  "onResponse")
                projectListObs.postValue(response.body())
            }
        })*/
        return projectListObs
    }
}