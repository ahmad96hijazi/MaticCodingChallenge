package com.hijazi.challenge.data.remote.model.mostStarredGithubRepo

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class GithubRepoResponse {

    @SerializedName("message")
    @Expose
    var message: String? = null
    @SerializedName("total_count")
    @Expose
    var totalCount: Int? = null
    @SerializedName("incomplete_results")
    @Expose
    var incompleteResults: Boolean? = true
    @SerializedName("items")
    @Expose
    var items: MutableList<Item>? = ArrayList()


}
