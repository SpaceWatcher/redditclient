package tserr.redditclient.data.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import tserr.redditclient.domain.NewsEntity

@Database(entities = [NewsEntity::class], version = 1, exportSchema = false)
abstract class RedditNewsDatabase : RoomDatabase() {
    abstract fun newsDao(): NewsDao

    companion object {
        fun create(context: Context): RedditNewsDatabase {
            return Room.databaseBuilder(context, RedditNewsDatabase::class.java, "reddit.db")
                    .fallbackToDestructiveMigration()
                    .build()
        }
    }
}