package com.co.routine.view.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.co.routine.MainApp

abstract class BaseActivity : AppCompatActivity() {
    protected lateinit var mainApp:MainApp
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mainApp = application as MainApp
    }
}