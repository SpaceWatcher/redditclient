package tserr.redditclient

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_main.*
import rx.Observable
import rx.Scheduler
import rx.android.schedulers.AndroidSchedulers
import rx.schedulers.Schedulers
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

        getNews()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        { news ->
                            newsList.adapter = NewsAdapter(news, this)
                            (newsList.adapter as NewsAdapter).notifyDataSetChanged()
                        },
                        { error ->
                            Toast.makeText(this, error.message, Toast.LENGTH_LONG).show()
                        }
                )


    }

    private fun getNews(): Observable<List<NewsItem>> {

        return Observable.create { subscriber ->

            val newsApi: NewsApi = RedditNews()
            val callResponse = newsApi.getNews("", "25")
            val response = callResponse.execute();

            if (response.isSuccessful) {
                val news = response.body()?.data?.children?.map {
                    with(it.data) {
                        NewsItem(title, author, subreddit, created, thumbnail, num_comments, score, url, permalink)
                    }
                }
                subscriber.onNext(news)
                subscriber.onCompleted()
            } else {
                subscriber.onError(Throwable(response.message()))
            }
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
