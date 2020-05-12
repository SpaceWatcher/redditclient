package tserr.redditclient.data

import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import tserr.redditclient.data.db.RedditNewsDatabase
import tserr.redditclient.data.network.RedditNewsApi
import tserr.redditclient.data.network.RedditNewsBoundaryCallback
import tserr.redditclient.domain.NewsRepository
import tserr.redditclient.view.MainViewModel

const val PAGE_SIZE = 150

@ExperimentalCoroutinesApi
val dataModule = module {

    single<RedditNewsApi> {
        Retrofit.Builder()
                .baseUrl("https://www.reddit.com")
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(RedditNewsApi::class.java)
    }

    single { RedditNewsDatabase.create(androidContext()).newsDao() }

    single { RedditNewsBoundaryCallback(get(), PAGE_SIZE) }

    scope<MainViewModel> {
        scoped<NewsRepository> {
            NetworkDbNewsRepository(getSource<MainViewModel>().viewModelScope, get(), get(), PAGE_SIZE)
        }
    }
}