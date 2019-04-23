package com.hijazi.challenge.view.activity

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.hijazi.challenge.view.callbacks.OnLoadMoreListener
import com.hijazi.challenge.R
import com.hijazi.challenge.data.remote.model.mostStarredGithubRepo.GithubRepoResponse
import com.hijazi.challenge.data.remote.repository.ResponseInterface
import com.hijazi.challenge.view.adapter.GithubRepoAdapter
import com.hijazi.challenge.view.adapter.PlaceHolderAdapter
import com.hijazi.challenge.viewModel.ObservableMostStarGithubRepo
import kotlinx.android.synthetic.main.activity_main.*
import java.util.*

class MainActivity : AppCompatActivity(), ResponseInterface<GithubRepoResponse>, GithubRepoAdapter.ItemClickListener {

    var loading = false

    private var githubRepoAdapter: GithubRepoAdapter? = null
    private var placeholderAdapter: PlaceHolderAdapter? = null

    private var observable: ObservableMostStarGithubRepo? = null
    private val observableChanged = Observer { _, newValue ->
        githubRepoAdapter?.setRepoResponse(newValue as GithubRepoResponse)
        loading = false
    }

    override fun onItemClick(view: View, position: Int) {
        // TODO("not implemented")
    }

    override fun removeWait() {
        shimmerContainer.visibility = View.GONE
        mainList.visibility = View.VISIBLE
        shimmerContainer.stopShimmer()
    }

    override fun getRequest(response: GithubRepoResponse) {
        // TODO("not implemented")
    }

    override fun onFailure(appErrMsg: String) {
        Toast.makeText(this, appErrMsg, Toast.LENGTH_LONG).show()
    }

    override fun showAndWait() {
        shimmerContainer.visibility = View.VISIBLE
        mainList.visibility = View.GONE
        shimmerContainer.startShimmer()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        prepareMainList()

        placeholderAdapter = PlaceHolderAdapter(64)

        placeholderList.setHasFixedSize(true)
        placeholderList.layoutManager = LinearLayoutManager(this)
        placeholderList.clipToPadding = false
        placeholderList.adapter = placeholderAdapter
    }

    private fun prepareMainList() {
        githubRepoAdapter = GithubRepoAdapter(this)
        githubRepoAdapter?.setClickListener(this)

        mainList.setHasFixedSize(true)
        mainList.layoutManager = LinearLayoutManager(this)
        mainList.clipToPadding = false
        mainList.adapter = githubRepoAdapter

        val visibleThreshold = 30
        val linearLayoutManager = mainList.layoutManager as LinearLayoutManager

        mainList.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                val totalItemCount = linearLayoutManager.itemCount
                val lastVisibleItem = linearLayoutManager.findLastVisibleItemPosition()
                if (!loading && totalItemCount <= lastVisibleItem + visibleThreshold) {
                    onLoadMoreListener.onLoadMore()
                    loading = true
                }
            }
        })

        observable = ObservableMostStarGithubRepo(this)
        observable!!.addObserver(observableChanged)
        observable!!.getMostStarredGithubRepo()
    }

    private var onLoadMoreListener: OnLoadMoreListener = object :
        OnLoadMoreListener {
        override fun onLoadMore() {
            observable!!.getMostStarredGithubRepo()
        }
    }
}
