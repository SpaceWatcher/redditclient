package tserr.redditclient

import android.text.format.DateUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

fun ViewGroup.inflate(layoutId: Int): View {
    return LayoutInflater.from(context).inflate(layoutId, this, false)
}

fun getPostDate(seconds: Long): CharSequence {
    val millis = seconds * 1000
    return DateUtils.getRelativeTimeSpanString(millis,
            System.currentTimeMillis(),
            DateUtils.MINUTE_IN_MILLIS,
            DateUtils.FORMAT_ABBREV_RELATIVE)
}