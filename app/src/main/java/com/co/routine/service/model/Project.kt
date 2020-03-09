package com.co.routine.service.model

import com.google.gson.annotations.SerializedName

data class Project(val id:Long,val name:String, @SerializedName("git_url") val gitUrl :String)