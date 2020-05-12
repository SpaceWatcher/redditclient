package tserr.redditclient.data.db

import androidx.paging.DataSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import tserr.redditclient.domain.NewsEntity

@Dao
interface NewsDao {
    @get:Query("SELECT * FROM NewsEntity")
    val dataSource: DataSource.Factory<Int, NewsEntity>

    @get:Query("SELECT * FROM NewsEntity")
    val all: List<NewsEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(news: List<NewsEntity>)

    @Query("DELETE FROM NewsEntity")
    suspend fun deleteAll()
}