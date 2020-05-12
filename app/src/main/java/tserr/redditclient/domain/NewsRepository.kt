package tserr.redditclient.domain

import androidx.lifecycle.LiveData
import androidx.paging.PagedList

interface NewsRepository {
    val pagedNewsPostsLive: LiveData<PagedList<NewsEntity>>

    val networkStateLive: LiveData<NetworkState>

    fun refreshNews()

    fun retryNewsLoad()
}