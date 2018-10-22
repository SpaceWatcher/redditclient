package tserr.redditclient


/*
data class NewsModel(

)
*/

data class NewsItem(
        val title: String,
        val author: String,
        val subReddit: String,
        val postDate: Long,
        val thumbnail: String,
        val numComments: Int,
        val rating: Int,
        val url: String,
        val link: String
)