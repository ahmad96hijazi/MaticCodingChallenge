package com.hijazi.challenge.data.remote.repository

interface ResponseInterface<T> {

    fun showAndWait()

    fun removeWait()

    fun getRequest(response: T)

    fun onFailure(appErrMsg: String)

}
