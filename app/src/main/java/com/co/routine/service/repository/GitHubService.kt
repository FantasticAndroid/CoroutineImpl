package com.co.routine.service.repository

import com.co.routine.service.model.Project
import retrofit2.http.GET
import retrofit2.http.Path

interface GitHubService {
    companion object{
        var HTTPS_API_GITHUB_URL = "https://api.github.com/"
    }

    @GET("users/{user}/repos")
    suspend fun getProjectList(@Path("user") user: String): List<Project>
}