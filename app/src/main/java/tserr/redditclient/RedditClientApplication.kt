package tserr.redditclient

import android.app.Application
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin
import tserr.redditclient.data.dataModule
import tserr.redditclient.view.viewModule

class RedditClientApplication : Application() {

    @ExperimentalCoroutinesApi
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(applicationContext)
            modules(dataModule + viewModule)
        }
    }
}