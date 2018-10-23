package tserr.redditclient.api

class NewsResponse(val data: DataResponse)

class DataResponse(
        val children: List<ChildrenResponse>,
        val after: String
)

class ChildrenResponse(val data: RedditNewsResponse)

class RedditNewsResponse(
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