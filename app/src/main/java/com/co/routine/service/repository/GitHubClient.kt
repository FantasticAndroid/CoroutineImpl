package com.co.routine.service.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.co.routine.service.model.Project
import com.co.routine.utils.Util
import com.google.gson.GsonBuilder
import com.google.gson.reflect.TypeToken
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.io.BufferedInputStream
import java.lang.reflect.Type
import java.net.HttpURLConnection
import java.net.URL

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

    suspend fun getProjectList() : List<Project>? {

        val url = URL(GitHubService.HTTPS_API_GITHUB_URL)
        val urlConnection = url.openConnection() as HttpURLConnection
        urlConnection.doOutput = true
        urlConnection.requestMethod = "GET"
        urlConnection.setRequestProperty("Content-Type", "application/json")
        if (urlConnection.responseCode == 200) {

            val inputStream = BufferedInputStream(urlConnection.inputStream)

            val response = Util.convertInputStreamToString(inputStream)
            val founderListType: Type = object : TypeToken<ArrayList<Project?>?>() {}.type
            return GsonBuilder().create().fromJson(response,founderListType)
            // From here you can convert the string to JSON with whatever JSON parser you like to use
            // After converting the string to JSON, I call my custom callback. You can follow this process too, or you can implement the onPostExecute(Result) method
        } else {
            return null
            // Status code is not 200
            // Do something to handle the error
        }
    }
}