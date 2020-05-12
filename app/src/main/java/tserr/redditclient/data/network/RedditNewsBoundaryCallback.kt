package tserr.redditclient.data.network

import androidx.paging.PagedList
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.channels.BroadcastChannel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.receiveAsFlow
import tserr.redditclient.domain.NetworkState
import tserr.redditclient.domain.NewsEntity

@ExperimentalCoroutinesApi
class RedditNewsBoundaryCallback(
        private val retrofitApi: RedditNewsApi,
        private val pageSize: Int
) : PagedList.BoundaryCallback<NewsEntity>() {

    private val networkStateChannel = BroadcastChannel<NetworkState>(Channel.CONFLATED)

    val networkStateFlow: Flow<NetworkState>
        get() = networkStateChannel.openSubscription().receiveAsFlow()

    private val loadAfterChannel = BroadcastChannel<String>(Channel.CONFLATED)

    private lateinit var retryAfter: String

    val newsEntitiesFlow: Flow<List<NewsEntity>>
        get() = loadAfterChannel.openSubscription().receiveAsFlow()
                .onEach { after ->
                    retryAfter = after
                    networkStateChannel.send(NetworkState.Loading)
                }
                .map(::loadItems)
                .map { response -> response.asNewsEntities }
                .onEach { networkStateChannel.send(NetworkState.Loaded) }

    override fun onZeroItemsLoaded() {
        loadAfterChannel.offer("")
    }

    override fun onItemAtEndLoaded(itemAtEnd: NewsEntity) {
        itemAtEnd.after?.let(loadAfterChannel::offer)
    }

    fun retryLoadItems() = loadAfterChannel.offer(retryAfter)

    suspend fun handleError(e: Throwable) = networkStateChannel.send(NetworkState.Error(e.message))

    private suspend fun loadItems(after: String): NewsResponse =
            retrofitApi.getTopNews(pageSize, after)
}

