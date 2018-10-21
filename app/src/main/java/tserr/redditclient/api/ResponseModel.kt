package tserr.redditclient.api

class NewsResponse(val data: DataResponse)

class DataResponse(
        val children: List<RedditNewsResponse>,
        val after: String?,
        val before: String?
)

class RedditNewsResponse(
        val title: String,
        val author: String,
        val subreddit: String,
        val created: Long,
        val permalink: String,
        val num_comments: Int,
        val score: Int,
        val thumbnail: String,
        val url: String
)