package tserr.redditclient.api.reddit

import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory;
import tserr.redditclient.api.NewsApi
import tserr.redditclient.api.NewsResponse

class RedditNews : NewsApi {

    private val redditApi: RedditApi

    init {
        val retrofit = Retrofit.Builder()
                .baseUrl("https://www.reddit.com")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        redditApi = retrofit.create(RedditApi::class.java);
    }

    override fun getNews(after: String, limit: String): Call<NewsResponse> {
        return  redditApi.getTopNews(after, limit)
    }
}