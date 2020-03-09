package com.co.routine.service.repository

import android.util.Log
import com.co.routine.service.model.Project
import com.co.routine.utils.Util
import com.google.gson.GsonBuilder
import com.google.gson.reflect.TypeToken
import java.io.BufferedInputStream
import java.lang.reflect.Type
import java.net.HttpURLConnection
import java.net.URL
import java.net.URLEncoder
import javax.net.ssl.HttpsURLConnection

object GitHubHttpClient {

    const val TAG = "GitHubHttpClient"

    /**
     * No need to make this function as suspend as we are not calling any other suspend function from this.
     * @return List<Project>?
     */
    fun getProjectList(): List<Project>? {

        val url = URL(GitHubService.HTTPS_API_GITHUB_URL + "users/google/repos")
        //val url = URL("https://jsonplaceholder.typicode.com/todos/1")

        Log.d(TAG, "url: " + url)
        val urlConnection = url.openConnection() as HttpsURLConnection
        urlConnection.readTimeout = 20000
        urlConnection.connectTimeout = 20000
        //urlConnection.doOutput = true
        urlConnection.doInput = true
        urlConnection.requestMethod = "GET"
        //urlConnection.setRequestProperty("Content-Type", "application/json")
        val statusCode = urlConnection.responseCode

        Log.d(TAG, "statusCode: " + statusCode)

        /*var s : String?=null
        s!!.length*/

        if (statusCode == 200) {

            val inputStream = BufferedInputStream(urlConnection.inputStream)

            val response = Util.convertInputStreamToString(inputStream)
            Log.d(TAG, "RESP: " + response)
            val founderListType: Type = object : TypeToken<ArrayList<Project?>?>() {}.type
            return GsonBuilder().create().fromJson(response, founderListType)
            // From here you can convert the string to JSON with whatever JSON parser you like to use
            // After converting the string to JSON, I call my custom callback. You can follow this process too, or you can implement the onPostExecute(Result) method
        } else {
            return null
            // Status code is not 200
            // Do something to handle the error
        }
    }
}