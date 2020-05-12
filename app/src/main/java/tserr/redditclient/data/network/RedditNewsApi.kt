package tserr.redditclient.data.network

import retrofit2.http.GET
import retrofit2.http.Query

interface RedditNewsApi {

    @GET("/top.json")
    suspend fun getTopNews(
            @Query("limit") limit: Int,
            @Query("after") after: String = ""
    ): NewsResponse
}