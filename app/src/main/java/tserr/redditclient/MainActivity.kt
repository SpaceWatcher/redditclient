package tserr.redditclient

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import kotlinx.android.synthetic.main.activity_main.*
import rx.Observable
import tserr.redditclient.api.NewsApi
import tserr.redditclient.api.reddit.RedditNews
import tserr.redditclient.ui.NewsAdapter

class MainActivity : AppCompatActivity() {

    private val newsList by lazy {
        news_list.apply {
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(news_list.context)
            addOnScrollListener(object : RecyclerView.OnScrollListener() {

            })
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initToolbar()



    }

    private fun getNews(): Observable<List<NewsItem>> {
        val newsApi: NewsApi = RedditNews()
        val callResponse = newsApi.getNews("", "25")
        val response = callResponse.execute();

        if (response.isSuccessful) {
            val news = response.body()?.data?.children?.map {
                with(it) {
                    NewsItem(title, author, subreddit, created, thumbnail, num_comments, score, url)
                }
            }

            newsList.adapter = NewsAdapter(news!!)
            return Observable.create(news)
        }
    }

    private fun initToolbar() {
        setSupportActionBar(toolbar)
        val actionBar = supportActionBar

        actionBar!!.setTitle(R.string.app_name)
        actionBar.setDisplayShowHomeEnabled(true)

        actionBar.setLogo(R.drawable.ic_reddit_logo)
        actionBar.setDisplayUseLogoEnabled(true)
    }

    fun requestNews() {

    }
}
