package tserr.redditclient.data

import androidx.lifecycle.LiveData
import androidx.lifecycle.asLiveData
import androidx.paging.PagedList
import androidx.paging.toLiveData
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.asExecutor
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.retry
import tserr.redditclient.data.db.NewsDao
import tserr.redditclient.data.network.RedditNewsBoundaryCallback
import tserr.redditclient.domain.NetworkState
import tserr.redditclient.domain.NewsEntity
import tserr.redditclient.domain.NewsRepository

@ExperimentalCoroutinesApi
class NetworkDbNewsRepository(
        private val coroutineScope: CoroutineScope,
        private val newsBoundaryCallback: RedditNewsBoundaryCallback,
        private val newsDao: NewsDao,
        private val pageSize: Int
) : NewsRepository {

    init {
        collectInsertingToDbLoadedItems()
    }

    override val networkStateLive: LiveData<NetworkState>
        get() = newsBoundaryCallback.networkStateFlow
                .asLiveData(coroutineScope.coroutineContext)


    override val pagedNewsPostsLive: LiveData<PagedList<NewsEntity>>
        get() = newsDao.dataSource.toLiveData(
                fetchExecutor = Dispatchers.Default.asExecutor(),
                config = PagedList.Config.Builder()
                        .setPrefetchDistance(25)
                        .setInitialLoadSizeHint(pageSize)
                        .setPageSize(pageSize)
                        .build(),
                boundaryCallback = newsBoundaryCallback
        )

    override fun refreshNews() {
        newsBoundaryCallback.onZeroItemsLoaded()
    }

    override fun retryNewsLoad() {
        newsBoundaryCallback.retryLoadItems()
    }

    private fun collectInsertingToDbLoadedItems() {
        newsBoundaryCallback.newsEntitiesFlow
                .onEach(newsDao::insertAll)
                .flowOn(Dispatchers.Default)
                .retry { cause ->
                    newsBoundaryCallback.handleError(cause)
                    true
                }
                .launchIn(coroutineScope)
    }
}