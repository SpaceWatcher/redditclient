package tserr.redditclient.data.network

import tserr.redditclient.domain.NewsEntity

val NewsResponse.asNewsEntities: List<NewsEntity>
    get() = data.children.map { newsPostResponse ->
        newsPostResponse.data.run {
            NewsEntity(
                    after = data.after,
                    title = title,
                    author = author,
                    subReddit = subreddit,
                    postDate = created_utc,
                    thumbnail = thumbnail,
                    numComments = num_comments,
                    rating = score,
                    url = permalink
            )
        }
    }