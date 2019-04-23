package com.hijazi.challenge.viewModel

import com.hijazi.challenge.data.remote.model.mostStarredGithubRepo.GithubRepoResponse
import com.hijazi.challenge.data.remote.repository.MostStarredGithubRepository
import com.hijazi.challenge.data.remote.repository.ResponseInterface
import timber.log.Timber
import java.io.Serializable
import java.util.*
import java.util.concurrent.atomic.AtomicInteger

class ObservableMostStarGithubRepo(
    private val view: ResponseInterface<GithubRepoResponse>
) : Observable(), Serializable, ResponseInterface<GithubRepoResponse> {
    override fun showAndWait() {
        view.showAndWait()
    }

    override fun removeWait() {
        view.removeWait()
    }

    override fun getRequest(response: GithubRepoResponse) {
        addResponseToValue(response)
    }

    override fun onFailure(appErrMsg: String) {
        page!!.decrementAndGet()
        view.onFailure(appErrMsg)
    }

    private var repository: MostStarredGithubRepository? = MostStarredGithubRepository(this)
    private var page: AtomicInteger? = AtomicInteger()
    private var value: GithubRepoResponse? = GithubRepoResponse()
        set(value) {
            field = value
            this.setChanged()
            this.notifyObservers(value)
        }

    init {
        page!!.set(1)
    }

    fun getMostStarredGithubRepo() {
        if (value != null && value?.message == null) {
            repository?.getMostStarredGithubRepo(page!!.getAndIncrement())
        } else {
            view.removeWait()
        }
    }

    fun getItemCount(): Int? {
        return value?.items?.size
    }

    private fun addResponseToValue(response: GithubRepoResponse) {
        value?.totalCount = response.totalCount
        value?.incompleteResults = response.incompleteResults
        response.items?.let { value?.items?.addAll(it) }
        this.setChanged()
        this.notifyObservers(value)
    }
}
