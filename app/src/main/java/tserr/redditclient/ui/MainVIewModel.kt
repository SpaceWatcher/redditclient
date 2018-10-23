package tserr.redditclient.ui

import android.arch.lifecycle.ViewModel
import rx.Observable
import rx.android.schedulers.AndroidSchedulers
import rx.schedulers.Schedulers
import tserr.redditclient.NewsModel
import tserr.redditclient.ApiNewsRepository
import tserr.redditclient.NewsItem
import tserr.redditclient.api.reddit.RedditNews

class MainViewModel(
): ViewModel() {

    private var newsModel: NewsModel = NewsModel()

    private val apiRepository by lazy {
        ApiNewsRepository(RedditNews())
    }

    fun requestNews(): Observable<List<NewsItem>> {
        return apiRepository.retrieveNews(newsModel.after)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnNext { newsModel = it }
                .map{ it.newsList }
    }


}