package tserr.redditclient.view

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import androidx.lifecycle.observe

open class Event<out T>(private val content: T) {

    var hasBeenHandled = false
        private set

    val notHandledValue: T?
        get() = if (hasBeenHandled) {
            null
        } else {
            hasBeenHandled = true
            content
        }
}

fun <T> LiveData<Event<T>>.observeEvent(owner: LifecycleOwner, observerBlock: (T) -> Unit) {
    observe(owner) { event -> event.notHandledValue?.let(observerBlock) }
}