package com.co.routine.view.ui

import android.os.Bundle
import android.widget.TextView
import com.co.routine.R
import com.co.routine.view.ui.fragments.MainListFragment
import com.co.routine.view.ui.fragments.MainListScopeFragment

class MainActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        ////val mainListFragment = MainListFragment.newInstance()
        val mainListFragment = MainListScopeFragment.newInstance()
        supportFragmentManager.beginTransaction().add(
            R.id.fragmentContainer,
            mainListFragment, mainListFragment.javaClass.simpleName
        ).commit()
    }
}
