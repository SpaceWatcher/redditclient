package tserr.redditclient.view

import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val viewModule = module{

    viewModel { MainViewModel() }

    single { NewsPagedListEpoxyController() }
}