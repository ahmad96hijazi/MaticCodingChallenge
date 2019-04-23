package com.hijazi.challenge.data.remote

import com.hijazi.challenge.data.remote.model.mostStarredGithubRepo.GithubRepoResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface NetworkService {

    /**
     * Get from github api
     *
     * @param page to get the rest of page
     * @return list of most starred Github repos that were created in the last 30 days
     */
    @GET("repositories?q=created:>2017-10-22&sort=stars&order=desc")
    fun getMostStarredGithubRepo(@Query("page") page: Int): Call<GithubRepoResponse>

}
