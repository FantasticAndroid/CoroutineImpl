package com.co.routine.view.ui

import android.os.Bundle
import android.widget.TextView
import com.co.routine.R
import com.co.routine.view.ui.fragments.MainListFragment

class MainActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val mainListFragment = MainListFragment.newInstance()
        supportFragmentManager.beginTransaction().add(
            R.id.fragmentContainer,
            mainListFragment, MainListFragment.TAG
        ).commit()
    }
}
