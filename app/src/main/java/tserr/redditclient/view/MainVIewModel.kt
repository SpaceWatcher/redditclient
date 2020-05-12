package tserr.redditclient.view

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.map
import androidx.paging.PagedList
import org.koin.ext.scope
import tserr.redditclient.domain.NetworkState
import tserr.redditclient.domain.NewsEntity
import tserr.redditclient.domain.NewsRepository

class MainViewModel : ViewModel() {

    private val newsRepository: NewsRepository = scope.get()

    val isRefreshingLive: LiveData<Boolean>
        get() = newsRepository.networkStateLive.map { state ->
            when {
                state is NetworkState.Error && state.message != null -> {
                    _errorMessageLive.value = Event(state.message)
                    false
                }
                else -> state == NetworkState.Loading
            }
        }

    private val _errorMessageLive = MutableLiveData<Event<String>>()

    val errorMessageLive: LiveData<Event<String>>
        get() = _errorMessageLive

    val pagedNewsListLive: LiveData<PagedList<NewsEntity>>
        get() = newsRepository.pagedNewsPostsLive

    fun onRefresh() = newsRepository.refreshNews()

    fun onRetry() = newsRepository.retryNewsLoad()

    fun onCreate() {
        newsRepository.networkStateLive
    }
}