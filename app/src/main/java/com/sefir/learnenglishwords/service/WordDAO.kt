package com.sefir.learnenglishwords.service

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.sefir.learnenglishwords.model.Word

@Dao
interface WordDAO {

    @Insert
    suspend fun insertAll(vararg word: Word): List<Long>

    @Insert
    suspend fun insertOneElement(word: Word): Long

    @Query("SELECT * FROM word")
    suspend fun getAllWord(): List<Word>

    @Query("SELECT * FROM word WHERE type LIKE 'own%'")
    suspend fun getOwnWord(): List<Word>

    @Query("SELECT * FROM word WHERE type LIKE 'own%'")
    suspend fun getOneOwnWord(): Word

    @Query("SELECT * FROM word WHERE type = :filter ORDER BY RANDOM() LIMIT 1")
    suspend fun getWord(filter: String): Word

    @Query("SELECT * FROM word WHERE uuid = :filter")
    suspend fun getWord(filter: Int): Word

    @Query("SELECT * FROM word WHERE type = :filter ORDER BY RANDOM() LIMIT 4")
    suspend fun getFourWord(filter: String): List<Word>

    @Query("DELETE FROM word")
    suspend fun deleteAllWord()

    @Query("DELETE FROM word WHERE uuid = :filter")
    suspend fun deleteOneElement(filter: Int)

    @Query("UPDATE word SET image = :str WHERE uuid = :filter")
    suspend fun update(filter: Int, str : String)
}