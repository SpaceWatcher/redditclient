package tserr.redditclient.ui

import android.support.v7.widget.RecyclerView
import android.view.ViewGroup
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.item_news.view.*
import android.net.Uri
import android.support.customtabs.CustomTabsIntent;
import tserr.redditclient.*

class NewsAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private var items: ArrayList<ViewItem> = ArrayList()

    init {
        items.add(LoadingItem)
    }

    fun addItems(newItems: List<NewsItem>) {
        val position = items.size - 1
        items.removeAt(position)
        notifyItemRemoved(position)
        items.addAll(newItems)
        items.add(LoadingItem)
        notifyItemRangeChanged(position, items.size + 1)
    }


    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (items[position].getViewType() == ViewType.NEWS)
            (holder as NewsViewHolder).bind(items[position])
    }

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onCreateViewHolder(view: ViewGroup, position: Int): RecyclerView.ViewHolder {

        return when (items[position].getViewType()) {
            ViewType.NEWS -> NewsViewHolder(view, items[position])
            ViewType.LOADING -> LoadingViewHolder(view)
        }
    }

    inner class LoadingViewHolder(parent: ViewGroup) : RecyclerView.ViewHolder(
            parent.inflate(R.layout.item_loading))

    inner class NewsViewHolder(view: ViewGroup)
        : RecyclerView.ViewHolder(view.inflate(R.layout.item_news)) {

        constructor(view: ViewGroup, item: ViewItem) : this(view) {
            bind(item)
        }

        fun bind(item: ViewItem) = with(itemView) {
            item as NewsItem
            title.text = item.title
            subreddit.text = itemView.context.getString(R.string.subreddit, item.subReddit)
            information.text = itemView.context.getString(R.string.info, item.author, getPostDate(item.postDate))
            comments.text = itemView.context.getString(R.string.comments, item.numComments)
            rating.text = item.rating.toString()
            Picasso.get().load(item.thumbnail).into(news_image)
            setOnClickListener {
                val url = "https://www.reddit.com${item.link}"
                val customTabsIntent = CustomTabsIntent.Builder()
                        .setToolbarColor(itemView.context.getColor(R.color.colorPrimaryDark))
                        .setSecondaryToolbarColor(itemView.context.getColor(R.color.colorAccent))
                        .build()

                customTabsIntent.launchUrl(itemView.context, Uri.parse(url))

            }
        }

    }
}

