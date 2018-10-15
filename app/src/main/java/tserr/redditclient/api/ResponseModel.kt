package tserr.redditclient.api


class DataResponse(
        val children: List<NewsResponse>,
        val after: String?,
        val before: String?
)

class NewsResponse(
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