package tserr.redditclient.data.network

data class NewsResponse(val data: DataResponse)

data class DataResponse(
        val children: List<ChildrenResponse>,
        val after: String?
)

data class ChildrenResponse(val data: RedditNewsResponse)

data class RedditNewsResponse(
        val title: String,
        val author: String,
        val subreddit: String,
        val created_utc: Long,
        val permalink: String,
        val num_comments: Int,
        val score: Int,
        val thumbnail: String,
        val url: String
)