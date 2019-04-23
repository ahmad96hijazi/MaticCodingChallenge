package com.hijazi.challenge.data.remote

import com.google.gson.annotations.SerializedName

internal class Response {
    @SerializedName("error_info")
    var errorInfo: String? = null
    @SerializedName("status")
    var status: String? = null

    constructor()

    constructor(status: String, errorInfo: String) {
        this.status = status
        this.errorInfo = errorInfo
    }
}
