package tserr.redditclient.ui

import android.arch.lifecycle.ViewModelProviders
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_main.*
import tserr.redditclient.R

class MainActivity : AppCompatActivity() {

    private val newsListView by lazy {
        news_list.apply {
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(news_list.context)
            adapter = NewsAdapter()
            addOnScrollListener(object : RecyclerView.OnScrollListener() {
                override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                    super.onScrollStateChanged(recyclerView, newState)
                    val totalItemCount = recyclerView.layoutManager?.itemCount
                    val lastPos = (recyclerView.layoutManager as LinearLayoutManager).findLastVisibleItemPosition()
                    if (totalItemCount == lastPos + 1) {
                        observeViewModel()
                    }
                }
            })
        }
    }

    private val viewModel: MainViewModel by lazy {
        ViewModelProviders.of(this).get(MainViewModel::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initToolbar()
        observeViewModel()
    }

    private fun observeViewModel() {
        viewModel.requestNews()
                .subscribe(
                        { news ->
                            (newsListView.adapter as NewsAdapter).addItems(news)
                        },
                        { error ->
                            Toast.makeText(this, error.message, Toast.LENGTH_LONG).show()
                        }
                )

    }

    private fun initToolbar() {
        setSupportActionBar(toolbar)
        val actionBar = supportActionBar

        actionBar!!.setTitle(R.string.app_name)
        actionBar.setDisplayShowHomeEnabled(true)

        actionBar.setLogo(R.drawable.ic_reddit_logo)
        actionBar.setDisplayUseLogoEnabled(true)
    }

}
