package tserr.redditclient.domain

sealed class NetworkState {
    object Loaded : NetworkState()
    object Loading : NetworkState()
    data class Error(val message: String?) : NetworkState()
}