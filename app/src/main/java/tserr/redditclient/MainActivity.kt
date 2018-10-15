package tserr.redditclient

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import kotlinx.android.synthetic.main.activity_main.*
import tserr.redditclient.api.NewsApi
import tserr.redditclient.api.reddit.RedditNews

class MainActivity : AppCompatActivity() {

    private val newsList by lazy {
        news_list.setHasFixedSize(true)
        news_list.layoutManager = LinearLayoutManager(news_list.context)
        news_list.addOnScrollListener(object : RecyclerView.OnScrollListener() {

        })
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val newsApi: NewsApi = RedditNews()
        val callResponse = newsApi.getNews("", "25")
        val response = callResponse.execute();

        if (response.isSuccessful) {
            val news = response.body()?.list?.map {
                NewsItem(it.title, it.author, it.postDate, it.thumbnail, it.numComments, it.url)
            }
        }

    }

    fun requestNews() {

    }
}
