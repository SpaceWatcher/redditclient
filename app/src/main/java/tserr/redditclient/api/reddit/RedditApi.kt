package tserr.redditclient.api.reddit

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query
import tserr.redditclient.api.DataResponse

interface RedditApi {

    @GET("/top.json")
    fun getTopNews(@Query("after") after: String, @Query("limit") limit: String): Call<DataResponse>
}