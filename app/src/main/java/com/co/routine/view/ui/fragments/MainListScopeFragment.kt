package com.co.routine.view.ui.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.co.routine.R
import com.co.routine.service.repository.GitHubClient
import com.co.routine.service.repository.GitHubHttpClient
import com.co.routine.view.ui.ProjectListAdapter
import kotlinx.android.synthetic.main.fragment_main_list.*
import kotlinx.coroutines.*
import kotlin.coroutines.CoroutineContext

// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * Assuming that our fragment is the scope, the background task should get canceled
 * as soon as the fragment is destroyed.
 * @property param1 String?
 * @property param2 String?
 * @property projectListAdapter ProjectListAdapter?
 * @property coroutineContext CoroutineContext
 * @property job Job
 */
class MainListScopeFragment private constructor() : BaseFragment(), CoroutineScope {
    private var param1: String? = null
    private var param2: String? = null
    private var projectListAdapter: ProjectListAdapter? = null

    private lateinit var job: Job

    override val coroutineContext: CoroutineContext
        get() = Dispatchers.Main + job + exceptionHandler

    private val exceptionHandler = CoroutineExceptionHandler { _, exception ->
        Log.e(TAG, "$exception handled !")
    }

    companion object {
        const val TAG = GitHubHttpClient.TAG

        @JvmStatic
        fun newInstance(param1: String = "", param2: String = "") =
            MainListScopeFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        job = Job()
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onDestroyView() {
        job.cancel()
        super.onDestroyView()
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

        implCoroutineToGetProjectList4()
    }

    private fun implCoroutineToGetProjectList1() {
        GlobalScope.launch(Dispatchers.Main) {
            val projectListObs = GitHubClient.getProjectList("google")
            projectListObs.observe(viewLifecycleOwner, Observer {
                projectListAdapter?.setProjectList(it)
            })
        }
    }

    private fun implCoroutineToGetProjectList2() {
        GlobalScope.launch(Dispatchers.Main) {
            val projectList = GlobalScope.async(Dispatchers.IO) {
                GitHubHttpClient.getProjectList()
            }
            projectListAdapter?.setProjectList(projectList.await())
        }
    }

    /**
     * withContext is nothing but an another way writing the async where we do not have to write await().
     */
    private fun implCoroutineToGetProjectList3() {
        GlobalScope.launch(Dispatchers.Main) {
            val projectList = withContext(Dispatchers.IO) {
                GitHubHttpClient.getProjectList()
            }
            Log.d(TAG, "projectList Found1")
            delay(5000)
            // IF fragment destroyed then also statement will be executed, because it is in Global Scope.
            Log.d(TAG, "projectList Found2")
            projectListAdapter?.setProjectList(projectList)
        }
    }

    /**
     * withContext is nothing but an another way writing the async where we do not have to write await().
     */
    private fun implCoroutineToGetProjectList4() {
        launch {
            val projectList = withContext(Dispatchers.IO) {
                GitHubHttpClient.getProjectList()
            }
            Log.d(TAG, "projectList Found1")
            delay(5000)
            // IF fragment destroyed then below statement will not be executed.
            Log.d(TAG, "projectList Found2")
            projectListAdapter?.setProjectList(projectList)
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
