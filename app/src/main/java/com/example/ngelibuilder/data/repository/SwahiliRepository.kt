package com.example.ngelibuilder.data.repository

import com.example.ngelibuilder.data.dao.NounClassDao
import com.example.ngelibuilder.data.dao.SwahiliWordDao
import com.example.ngelibuilder.data.dao.UserProgressDao
import com.example.ngelibuilder.data.entity.NounClass
import com.example.ngelibuilder.data.entity.SwahiliWord
import com.example.ngelibuilder.data.entity.UserProgress
import kotlinx.coroutines.flow.Flow

class SwahiliRepository (
    private val swahiliWordDao: SwahiliWordDao,
    private val nounClassDao: NounClassDao,
    private val userProgressDao: UserProgressDao

    ){

    //Word-related operations
    val allWords: Flow<List<SwahiliWord>> = swahiliWordDao.getAllWords()

    fun getWordsByClass(nounClass: String):  Flow<List<SwahiliWord>> {
        return swahiliWordDao.getWordsByClass(nounClass)
    }

    suspend fun insertWord(word: SwahiliWord){
        swahiliWordDao.insertWord(word)
    }

    //Noun Class operations
    val allNounClasses: Flow<List<NounClass>> = nounClassDao.getAllNounClasses()

    //User Progress operations
    fun getProgressForWord(wordId: Int): Flow<List<UserProgress>> {
        return userProgressDao.getProgressForWord(wordId)
    }

    suspend fun recordProgress(progress: UserProgress){
        userProgressDao.recordProgress(progress)
    }
}