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

    override fun onBindViewHolder(p0: RecyclerView.ViewHolder, p1: Int) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun getItemCount(): Int {
        return items.size
    }


    override fun onCreateViewHolder(view: ViewGroup, position: Int): RecyclerView.ViewHolder {

    }

    inner class NewsViewHolder(view: ViewGroup, val item: NewsItem)
        : RecyclerView.ViewHolder(view.inflate(R.layout.item_news)) {

        init {
            with(itemView) {
                title.text = item.title
                information.text = "Posted by ${item.author} ${getPostDate(item.postDate)}"
            }
        }

    }

    fun getPostDate(millis : Long): CharSequence {
        return DateUtils.getRelativeTimeSpanString(millis,
                System.currentTimeMillis(),
                DateUtils.MINUTE_IN_MILLIS,
                DateUtils.FORMAT_ABBREV_RELATIVE)
    }
}

fun ViewGroup.inflate(layoutId: Int): View {
    return LayoutInflater.from(context).inflate(layoutId, this, false)
}