package tserr.redditclient.view

import android.net.Uri
import android.widget.ImageView
import androidx.browser.customtabs.CustomTabsIntent
import androidx.cardview.widget.CardView
import androidx.databinding.BindingAdapter
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.squareup.picasso.Picasso
import tserr.redditclient.R

@BindingAdapter("source")
fun ImageView.source(url: String) {
    if (url.isNotEmpty())
        Picasso.get().load(url).into(this)
}

@BindingAdapter("newsLink")
fun CardView.setNewsLink(link: String) {
    setOnClickListener {
        CustomTabsIntent.Builder()
                .setToolbarColor(context.getColor(R.color.colorPrimaryDark))
                .setSecondaryToolbarColor(context.getColor(R.color.colorAccent))
                .build()
                .launchUrl(context, Uri.parse("https://www.reddit.com$link"))
    }
}

@BindingAdapter("accentColor")
fun SwipeRefreshLayout.setAccentColor(color: Int) {
    setColorSchemeColors(color)
}