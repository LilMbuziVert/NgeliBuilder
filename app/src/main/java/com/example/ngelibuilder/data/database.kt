package com.example.ngelibuilder.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import androidx.sqlite.db.SupportSQLiteDatabase
import com.example.ngelibuilder.data.dao.NounClassDao
import com.example.ngelibuilder.data.dao.SwahiliWordDao
import com.example.ngelibuilder.data.dao.UserProgressDao
import com.example.ngelibuilder.data.entity.NounClass
import com.example.ngelibuilder.data.entity.SwahiliWord
import com.example.ngelibuilder.data.entity.UserProgress
import com.example.ngelibuilder.data.util.Converters
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@Database(
    entities = [SwahiliWord::class, NounClass::class, UserProgress::class],
    version = 1
)

@TypeConverters(Converters::class)
abstract class SwahiliDatabase : RoomDatabase(){
    abstract fun swahiliWordDao(): SwahiliWordDao
    abstract fun nounClassDao(): NounClassDao
    abstract fun userProgressDao():  UserProgressDao

    companion object{
        @Volatile
        private var INSTANCE: SwahiliDatabase? = null

        fun getDatabase(context: Context, scope: CoroutineScope): SwahiliDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    SwahiliDatabase::class.java,
                    "swahili_database"
                )
                    .addCallback(SwahiliDatabaseCallback(scope))
                    .build()
                    INSTANCE = instance
                    instance
            }
        }

        private class SwahiliDatabaseCallback(
            private val scope: CoroutineScope
        ) : RoomDatabase.Callback() {
            override fun onCreate(db: SupportSQLiteDatabase) {
                super.onCreate(db)
                INSTANCE?.let{database ->
                    scope.launch(Dispatchers.IO){
                        //prepopulate database with initial data
                        populateDatabase(database)
                    }
                }
            }
        }

        suspend fun populateDatabase(database: SwahiliDatabase) {
            //Add initial noun classes
            val nounClassDao = database.nounClassDao()
            val initialNounClasses = listOf(
                NounClass(
                    "M-/WA-",
                    "People Class",
                    "Used for people and personified things",
                    "m-",
                    "wa-"
                ),
                NounClass(
                    "M-/MI-",
                    "Plants/Natural Things",
                    "Used for plants and natural phenomena",
                    "m-",
                    "mi-"
                ),
                NounClass(
                    "JI-/MA-",
                    "Large/Regular Objects",
                    "Used for various objects, fruits",
                    "ji-",
                    "ma-"
                ),
                NounClass(
                    "KI-/VI-",
                    "Tools/Small Things",
                    "Used for tools and small things",
                    "ki-",
                    "vi-"
                ),
                NounClass(
                    "N-",
                    "Animal/Foreign Words",
                    "Used for animals and many loanwords",
                    "n-",
                    "n-"
                ),
                NounClass(
                    "U-",
                    "Abstract Concepts",
                    "Used for abstract concepts",
                    "u-",
                    null)
            )
            nounClassDao.insertAllNounClasses(initialNounClasses)


            //Add initial words
            val wordDao = database.swahiliWordDao()
            val initialWords = listOf(
                // M-/WA- class (people)
                SwahiliWord(id = 1, word = "mtu", nounClass = "M-/WA-", meaning = "person", exampleSentence = "Mtu huyu ni mrefu.", difficultyLevel = 1),
                SwahiliWord(id = 2, word = "mwalimu", nounClass = "M-/WA-", meaning = "teacher", exampleSentence = "Mwalimu anafundisha Kiswahili.", difficultyLevel = 1),
                SwahiliWord(id = 3, word = "mtoto", nounClass = "M-/WA-", meaning = "child", exampleSentence = "Mtoto anacheza.", difficultyLevel = 1),
                SwahiliWord(id = 4, word = "mgeni", nounClass = "M-/WA-", meaning = "guest/stranger", exampleSentence = "Mgeni amefika leo.", difficultyLevel = 2),
                SwahiliWord(id = 5, word = "mkulima", nounClass = "M-/WA-", meaning = "farmer", exampleSentence = "Mkulima analima mahindi.", difficultyLevel = 2),

                // M-/MI- class (plants and some objects)
                SwahiliWord(id = 6, word = "mti", nounClass = "M-/MI-", meaning = "tree", exampleSentence = "Mti huu ni mrefu sana.", difficultyLevel = 1),
                SwahiliWord(id = 7, word = "mlango", nounClass = "M-/MI-", meaning = "door", exampleSentence = "Mlango uko wazi.", difficultyLevel = 1),
                SwahiliWord(id = 8, word = "mtama", nounClass = "M-/MI-", meaning = "millet", exampleSentence = "Mtama unakua vizuri.", difficultyLevel = 3),
                SwahiliWord(id = 9, word = "mto", nounClass = "M-/MI-", meaning = "river", exampleSentence = "Mto huu ni mrefu.", difficultyLevel = 1),
                SwahiliWord(id = 10, word = "mguu", nounClass = "M-/MI-", meaning = "leg", exampleSentence = "Mguu wake umeumia.", difficultyLevel = 2),

                // KI-/VI- class (things, languages, diminutives)
                SwahiliWord(id = 11, word = "kiti", nounClass = "KI-/VI-", meaning = "chair", exampleSentence = "Kiti hiki ni kipya.", difficultyLevel = 1),
                SwahiliWord(id = 12, word = "kiatu", nounClass = "KI-/VI-", meaning = "shoe", exampleSentence = "Kiatu changu kimepotea.", difficultyLevel = 1),
                SwahiliWord(id = 13, word = "kitabu", nounClass = "KI-/VI-", meaning = "book", exampleSentence = "Kitabu changu kiko mezani.", difficultyLevel = 1),
                SwahiliWord(id = 14, word = "kiswahili", nounClass = "KI-/VI-", meaning = "Swahili language", exampleSentence = "Ninajifunza Kiswahili.", difficultyLevel = 1),
                SwahiliWord(id = 15, word = "kikombe", nounClass = "KI-/VI-", meaning = "cup", exampleSentence = "Kikombe kina chai.", difficultyLevel = 1),

                // N- class (animals, many foreign words)
                SwahiliWord(id = 16, word = "ndizi", nounClass = "N-", meaning = "banana", exampleSentence = "Ndizi ni tamu.", difficultyLevel = 1),
                SwahiliWord(id = 17, word = "nyumba", nounClass = "N-", meaning = "house", exampleSentence = "Nyumba yetu ni kubwa.", difficultyLevel = 1),
                SwahiliWord(id = 18, word = "ng'ombe", nounClass = "N-", meaning = "cow", exampleSentence = "Ng'ombe anatoa maziwa.", difficultyLevel = 2),
                SwahiliWord(id = 19, word = "simba", nounClass = "N-", meaning = "lion", exampleSentence = "Simba ni mnyama mkali.", difficultyLevel = 1),
                SwahiliWord(id = 20, word = "ndege", nounClass = "N-", meaning = "bird/airplane", exampleSentence = "Ndege inapaa angani.", difficultyLevel = 2),

                // U- class (abstract concepts, some objects)
                SwahiliWord(id = 21, word = "uhuru", nounClass = "U-", meaning = "freedom", exampleSentence = "Tunapenda uhuru wetu.", difficultyLevel = 2),
                SwahiliWord(id = 22, word = "utamaduni", nounClass = "U-", meaning = "culture", exampleSentence = "Utamaduni wetu ni muhimu.", difficultyLevel = 3),
                SwahiliWord(id = 23, word = "ugonjwa", nounClass = "U-", meaning = "sickness", exampleSentence = "Ana ugonjwa wa malaria.", difficultyLevel = 2),
                SwahiliWord(id = 24, word = "upendo", nounClass = "U-", meaning = "love", exampleSentence = "Upendo ni muhimu katika familia.", difficultyLevel = 2),
                SwahiliWord(id = 25, word = "ugali", nounClass = "U-", meaning = "cornmeal porridge", exampleSentence = "Ugali ni chakula cha kawaida.", difficultyLevel = 1),

                // JI-/MA- class (large things, augmentatives, some fruits)
                SwahiliWord(id = 26, word = "jicho", nounClass = "JI-/MA-", meaning = "eye", exampleSentence = "Jicho lake ni jeusi.", difficultyLevel = 2),
                SwahiliWord(id = 27, word = "jina", nounClass = "JI-/MA-", meaning = "name", exampleSentence = "Jina lako ni nani?", difficultyLevel = 1),
                SwahiliWord(id = 28, word = "embe", nounClass = "JI-/MA-", meaning = "mango", exampleSentence = "Embe hili ni tamu.", difficultyLevel = 1),
                SwahiliWord(id = 29, word = "gari", nounClass = "JI-/MA-", meaning = "car", exampleSentence = "Gari lake ni jeupe.", difficultyLevel = 1),
                SwahiliWord(id = 30, word = "jiji", nounClass = "JI-/MA-", meaning = "city", exampleSentence = "Jiji la Dar es Salaam ni kubwa.", difficultyLevel = 2),

            )
            wordDao.insertAllWords(initialWords)
        }
    }
}

