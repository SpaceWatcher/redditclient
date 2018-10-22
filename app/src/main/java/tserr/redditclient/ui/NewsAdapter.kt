package tserr.redditclient.ui

import android.app.Activity
import android.content.Context
import android.support.v7.widget.RecyclerView
import android.text.format.DateUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.item_news.view.*
import tserr.redditclient.NewsItem
import tserr.redditclient.R
import android.support.v4.content.ContextCompat.startActivity
import android.content.Intent
import android.net.Uri
import android.support.customtabs.CustomTabsIntent
import java.security.AccessControlContext
import java.security.PrivateKey


class NewsAdapter(
        private var items: List<NewsItem>,
        private var activity: Activity
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        (holder as NewsViewHolder).bind(items[position])
    }

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onCreateViewHolder(view: ViewGroup, position: Int): RecyclerView.ViewHolder {
        val holder = NewsViewHolder(view)
        holder.bind(items[position])
        return holder
    }

    fun getPostDate(seconds: Long): CharSequence {
        val millis = seconds * 1000
        return DateUtils.getRelativeTimeSpanString(millis,
                System.currentTimeMillis(),
                DateUtils.MINUTE_IN_MILLIS,
                DateUtils.FORMAT_ABBREV_RELATIVE)
    }

    inner class NewsViewHolder(view: ViewGroup)
        : RecyclerView.ViewHolder(view.inflate(R.layout.item_news)) {

        fun bind(item: NewsItem) = with(itemView) {
            title.text = item.title
            subreddit.text = activity.getString(R.string.subreddit, item.subReddit)
            information.text = activity.getString(R.string.info, item.author, getPostDate(item.postDate))
            comments.text = activity.getString(R.string.comments, item.numComments)
            rating.text = item.rating.toString()
            Picasso.get().load(item.thumbnail).into(news_image)
            setOnClickListener {
                val url = "https://www.reddit.com${item.link}"
                val customTabsIntent = CustomTabsIntent.Builder()
                        .setToolbarColor(activity.getColor(R.color.colorPrimaryDark))
                        .setSecondaryToolbarColor(activity.getColor(R.color.colorAccent))
                        .build()

                customTabsIntent.launchUrl(activity, Uri.parse(url))

            }
        }

    }
}

fun ViewGroup.inflate(layoutId: Int): View {
    return LayoutInflater.from(context).inflate(layoutId, this, false)
}