package com.example.ngelibuilder.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.ngelibuilder.data.entity.NounClass
import com.example.ngelibuilder.data.entity.SwahiliWord
import com.example.ngelibuilder.data.entity.UserProgress
import kotlinx.coroutines.flow.Flow

@Dao
interface SwahiliWordDao {
    @Query("SELECT * FROM swahili_words")
    fun getAllWords(): Flow<List<SwahiliWord>>

    @Query("SELECT * FROM swahili_words WHERE nounClass = :nounClass")
    fun getWordsByClass(nounClass: String): Flow<List<SwahiliWord>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertWord(word: SwahiliWord)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAllWords(words: List<SwahiliWord>)

}

@Dao
interface NounClassDao{
    @Query("SELECT * FROM noun_classes")
    fun getAllNounClasses(): Flow<List<NounClass>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertNounClass(nounClass: NounClass)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAllNounClasses(nounClasses: List<NounClass>)
}

@Dao
interface UserProgressDao{
    @Query("SELECT * FROM user_progress WHERE wordId = :wordId")
    fun getProgressForWord(wordId: Int): Flow<List<UserProgress>>

    @Insert
    suspend fun recordProgress(progress: UserProgress)


}