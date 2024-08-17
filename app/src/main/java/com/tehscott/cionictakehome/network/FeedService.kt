package com.tehscott.cionictakehome.network

import com.tehscott.cionictakehome.models.remote.FeedItemRemote
import retrofit2.Call
import retrofit2.http.GET

interface FeedService {
    @GET("posts/")
    fun getFeed(): Call<List<FeedItemRemote>>

    companion object {
        const val BASE_URL = "https://jsonplaceholder.typicode.com/"
    }
}