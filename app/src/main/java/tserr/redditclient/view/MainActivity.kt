package tserr.redditclient.view

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.observe
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.snackbar.Snackbar
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel
import tserr.redditclient.R
import tserr.redditclient.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private val mainViewModel: MainViewModel by viewModel()

    private val epoxyController: NewsPagedListEpoxyController by inject()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        DataBindingUtil.setContentView<ActivityMainBinding>(this, R.layout.activity_main).apply {
            lifecycleOwner = this@MainActivity
            viewModel = mainViewModel
            newsList.apply {
                layoutManager = LinearLayoutManager(this@MainActivity)
                adapter = epoxyController.adapter
            }
        }

        mainViewModel.pagedNewsListLive.observe(this, epoxyController::submitList)

        mainViewModel.errorMessageLive.observeEvent(this) { message ->
            Snackbar.make(findViewById(android.R.id.content), message, Snackbar.LENGTH_INDEFINITE)
                    .setAction(R.string.retry) { mainViewModel.onRetry() }
                    .setActionTextColor(getColor(R.color.colorPrimaryDark))
                    .show()
        }

        mainViewModel.onCreate()
    }
}
