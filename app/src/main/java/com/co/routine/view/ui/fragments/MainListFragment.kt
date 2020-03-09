package com.co.routine.view.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.co.routine.R
import com.co.routine.service.repository.GitHubClient
import com.co.routine.view.ui.ProjectListAdapter
import com.co.routine.viewmodel.MainListViewModel
import kotlinx.android.synthetic.main.fragment_main_list.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

class MainListFragment private constructor() : BaseFragment() {
    private var param1: String? = null
    private var param2: String? = null
    private var projectListAdapter: ProjectListAdapter? = null

    companion object {
        const val TAG = "MainListFragment"

        @JvmStatic
        fun newInstance(param1: String = "", param2: String = "") =
            MainListFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_main_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        projectListAdapter = ProjectListAdapter(mainApp)

        projectRv.layoutManager = LinearLayoutManager(mainApp, RecyclerView.VERTICAL, false)
        projectRv.adapter = projectListAdapter

        implCoroutineToGetProjectList()
    }

    private fun implCoroutineToGetProjectList() {
        GlobalScope.launch(Dispatchers.Main) {
            val projectListObs = GitHubClient.getProjectList("google")
            projectListObs.observe(viewLifecycleOwner, Observer {
                projectListAdapter?.setProjectList(it)
            })
        }
    }

    /*private fun implViewModel(){
        val mainListViewModel = ViewModelProvider.AndroidViewModelFactory.getInstance(mainApp).
        create(MainListViewModel::class.java)
        observeViewModel(mainListViewModel)
    }*/
    /*private fun observeViewModel(mainListViewModel: MainListViewModel){
        mainListViewModel.projectListObs.observe(viewLifecycleOwner, Observer {
            projectListAdapter?.setProjectList(it)
        })
    }*/
}
