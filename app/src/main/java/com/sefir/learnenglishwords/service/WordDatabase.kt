package com.sefir.learnenglishwords.service

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.sefir.learnenglishwords.model.Word

@Database(entities = arrayOf(Word::class), version = 1)
abstract class WordDatabase : RoomDatabase() {

    abstract fun wordDao(): WordDAO

    companion object {

        @Volatile
        var instance: WordDatabase? = null

        private val lock = Any()

        operator fun invoke(context: Context) = instance ?: synchronized(lock) {
            instance ?: createDatabase(context).also {
                instance = it
            }
        }


        private fun createDatabase(context: Context) = Room.databaseBuilder(
            context.applicationContext,
            WordDatabase::class.java, "worddatabase"
        ).build()


    }
}