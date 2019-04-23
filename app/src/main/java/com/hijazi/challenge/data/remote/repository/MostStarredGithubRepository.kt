package com.hijazi.challenge.data.remote.repository

import com.hijazi.challenge.data.remote.NetworkError
import com.hijazi.challenge.data.remote.Service
import com.hijazi.challenge.data.remote.model.mostStarredGithubRepo.GithubRepoResponse
import timber.log.Timber

class MostStarredGithubRepository(
    private val view: ResponseInterface<GithubRepoResponse>
) {

    private var service: Service? = null

    init {
        service = Service()
    }

    fun getMostStarredGithubRepo(page: Int) {
        view.showAndWait()
        service?.getMostStarredGithubRepo(object : Service.ResponseCallBack<GithubRepoResponse> {
            override fun onSuccess(response: GithubRepoResponse) {
                view.getRequest(response)
                view.removeWait()
            }

            override fun onError(networkError: NetworkError) {
                if (networkError.isResponseNull)
                    view.onFailure(networkError.appErrorMessage!!)
                view.removeWait()
            }
        }, page)
    }
}
