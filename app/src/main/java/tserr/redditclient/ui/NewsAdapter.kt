package tserr.redditclient.ui

import android.support.v7.widget.RecyclerView
import android.text.format.DateUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.item_news.view.*
import tserr.redditclient.NewsItem
import tserr.redditclient.R
import java.util.*

class NewsAdapter(
        private var items: ArrayList<NewsItem> = ArrayList()
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        holder as NewsViewHolder
        holder.bind(items[position])
    }

    override fun getItemCount(): Int {
        return items.size
    }


    override fun onCreateViewHolder(view: ViewGroup, position: Int): RecyclerView.ViewHolder {
        val holder = NewsViewHolder(view)
        holder.bind(items[position])
        return holder
    }

    fun getPostDate(millis : Long): CharSequence {
        return DateUtils.getRelativeTimeSpanString(millis,
                System.currentTimeMillis(),
                DateUtils.MINUTE_IN_MILLIS,
                DateUtils.FORMAT_ABBREV_RELATIVE)
    }

    inner class NewsViewHolder(view: ViewGroup)
        : RecyclerView.ViewHolder(view.inflate(R.layout.item_news)) {

        fun bind(item: NewsItem) = with(itemView) {
            title.text = item.title
            subreddit.text = "r/${item.subReddit}"
            information.text = "Posted by u/${item.author} ${getPostDate(item.postDate)}"
            comments.text = "${item.numComments} comments"
            rating.text = "${item.rating} rating"
        }

    }
}

fun ViewGroup.inflate(layoutId: Int): View {
    return LayoutInflater.from(context).inflate(layoutId, this, false)
}