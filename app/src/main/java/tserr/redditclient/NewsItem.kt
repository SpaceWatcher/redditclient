package tserr.redditclient

data class NewsItem(
    val title: String,
    val author: String,
    val postDate: Long,
    val thumbnail: String,
    val numComments: Int,
    val url: String
)