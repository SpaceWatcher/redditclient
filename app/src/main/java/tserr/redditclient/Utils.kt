package tserr.redditclient

import android.text.format.DateUtils

fun formatPostDate(seconds: Long): CharSequence =
        DateUtils.getRelativeTimeSpanString(
                seconds * 1000,
                System.currentTimeMillis(),
                DateUtils.MINUTE_IN_MILLIS,
                DateUtils.FORMAT_ABBREV_RELATIVE
        )