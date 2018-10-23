package tserr.redditclient

import rx.Observable
import tserr.redditclient.api.NewsApi

interface NewsRepository {
    fun retrieveNews(after: String): Observable<NewsModel>
}


class ApiNewsRepository(
        private val newsApi: NewsApi
) : NewsRepository {

    override fun retrieveNews(after: String): Observable<NewsModel> {
        return Observable.create { subscriber ->

            val response = newsApi.getNews(after, "25").execute();

            if (response.isSuccessful) {
                val news = response.body()?.data?.children?.map {
                    with(it.data) {
                        NewsItem(title, author, subreddit, created_utc, thumbnail, num_comments, score, url, permalink)
                    }
                }
                subscriber.onNext(NewsModel(news!!, response.body()?.data?.after!!))
                subscriber.onCompleted()
            } else {
                subscriber.onError(Throwable(response.message()))
            }
        }
    }

}