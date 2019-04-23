package com.hijazi.challenge.data.remote

import com.hijazi.challenge.data.remote.model.mostStarredGithubRepo.GithubRepoResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import timber.log.Timber

class Service {

    fun getMostStarredGithubRepo(responseCallBack: ResponseCallBack<GithubRepoResponse>, page: Int) {
        val apiInterface = ApiClient.client!!.create(NetworkService::class.java)
        val call = apiInterface.getMostStarredGithubRepo(page)
        call.enqueue(object : Callback<GithubRepoResponse> {
            override fun onResponse(call: Call<GithubRepoResponse>, response: Response<GithubRepoResponse>) {
                if (response.isSuccessful) {
                    response.body()?.let { responseCallBack.onSuccess(it) }
                } else {
                    responseCallBack.onError(NetworkError(null))
                }
            }

            override fun onFailure(call: Call<GithubRepoResponse>, t: Throwable) {
                t.printStackTrace()
                responseCallBack.onError(NetworkError(t))
            }
        })
    }

    interface ResponseCallBack<T> {
        fun onSuccess(response: T)

        fun onError(networkError: NetworkError)
    }
}
