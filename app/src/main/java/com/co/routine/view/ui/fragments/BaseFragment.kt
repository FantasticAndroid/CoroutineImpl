package com.co.routine.view.ui.fragments

import android.content.Context
import androidx.fragment.app.Fragment
import com.co.routine.MainApp

abstract class BaseFragment : Fragment() {

    protected lateinit var mainApp:MainApp
    override fun onAttach(context: Context) {
        super.onAttach(context)
        mainApp = context.applicationContext as MainApp
    }
}