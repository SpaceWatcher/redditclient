package tserr.redditclient.view

import com.airbnb.epoxy.EpoxyModel
import com.airbnb.epoxy.paging.PagedListEpoxyController
import tserr.redditclient.ItemNewsBindingModel_
import tserr.redditclient.domain.NewsEntity

class NewsPagedListEpoxyController : PagedListEpoxyController<NewsEntity>() {

    override fun buildItemModel(currentPosition: Int, item: NewsEntity?): EpoxyModel<*> =
            item?.let {
                ItemNewsBindingModel_()
                        .id(item.newsId)
                        .newsItem(item)
            } ?: ItemNewsBindingModel_()
                    .id(-currentPosition)
                    .newsItem(NewsEntity())
}