package tserr.redditclient.domain

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class NewsEntity(
        val after: String? = null,
        val title: String = "",
        val author: String = "",
        val subReddit: String = "",
        val postDate: Long = 0,
        val thumbnail: String = "",
        val numComments: Int = 0,
        val rating: Int = 0,
        val url: String = "",
        @PrimaryKey(autoGenerate = true)
        val newsId: Int = 0
)