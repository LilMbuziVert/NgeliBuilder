package com.example.ngelibuilder

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity


class MainActivity : AppCompatActivity(){

    private lateinit var questionTextView: TextView
    private lateinit var scoreTextView: TextView
    private lateinit var optionOneButton: Button
    private lateinit var optionTwoButton: Button
    private lateinit var optionThreeButton: Button
    private lateinit var optionFourButton: Button
    private lateinit var nextButton: Button

    private lateinit var wordList: List<SwahiliWord>
    private lateinit var currentWord: SwahiliWord
    private lateinit var allClasses: List<String>
    private var correctAnswers = 0
    private var totalQuestions = 0

    override fun onCreate(savedInstanceState: Bundle?){
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Initialise UI components
        questionTextView = findViewById(R.id.questionTextView)
        scoreTextView = findViewById(R.id.scoreTextView)
        optionOneButton = findViewById(R.id.optionOneButton)
        optionTwoButton = findViewById(R.id.optionTwoButton)
        optionThreeButton = findViewById(R.id.optionThreeButton)
        optionFourButton = findViewById(R.id.optionFourButton)
        nextButton = findViewById(R.id.nextButton)

        //Initialise data
        wordList = initaliseWordList()
        allClasses = getAllNounClasses()

        // Set up the first question
        setupNextQuestion()

        // Set up button click listeners
        optionOneButton.setOnClickListener{ checkAnswer(optionOneButton.text.toString()) }
        optionTwoButton.setOnClickListener{ checkAnswer(optionTwoButton.text.toString()) }
        optionThreeButton.setOnClickListener{ checkAnswer(optionThreeButton.text.toString()) }
        optionFourButton.setOnClickListener{ checkAnswer(optionFourButton.text.toString()) }
        nextButton.setOnClickListener{ setupNextQuestion() }

    }

    private fun setupNextQuestion() {

        //Enable all option buttons
        optionOneButton.isEnabled = true
        optionTwoButton.isEnabled = true
        optionThreeButton.isEnabled = true
        optionFourButton.isEnabled = true

        //Hide the next button until answer is selected
        nextButton.visibility = View.INVISIBLE

        //select a random word
        currentWord = wordList.random()

        //show the question
        "Which noun class (ngeli) does \"${currentWord.word}\" belong to?".also { questionTextView.text = it }

        // create multiple choice (1 correct, 3 wrong)
        val choices = mutableListOf<String>()
        choices.add(currentWord.nounClass)

        // Add 3 wrong choices (ensure they are unique and don't match the correct choice)
        val wrongClasses = allClasses.filterNot {it == currentWord.nounClass}

        for (i in 0 until minOf(3, wrongClasses.size)){
            choices.add(wrongClasses[i])
        }

        // Shuffle choices and set to buttons
        choices.shuffle()
        optionOneButton.text = choices [0]
        optionTwoButton.text = choices [1]
        optionThreeButton.text = choices [2]
        optionFourButton.text = choices [3]

        //Reset button backgrounds
        resetButtonBackgrounds()

        //Increment total questions
        totalQuestions++
        updateScore()

    }

    private fun resetButtonBackgrounds(){
        optionOneButton.setBackgroundResource(android.R.drawable.btn_default)
        optionTwoButton.setBackgroundResource(android.R.drawable.btn_default)
        optionThreeButton.setBackgroundResource(android.R.drawable.btn_default)
        optionFourButton.setBackgroundResource(android.R.drawable.btn_default)
    }

    private fun checkAnswer( selectedAnswer: String) {

        //Disable all option buttons to prevent multiple selections
        optionOneButton.isEnabled =  false
        optionTwoButton.isEnabled =  false
        optionThreeButton.isEnabled =  false
        optionFourButton.isEnabled =  false

        //Show next button
        nextButton.visibility = View.VISIBLE

        //Check if the answer is correct
        if (selectedAnswer == currentWord.nounClass) {
            correctAnswers++
            Toast.makeText(this, "Correct!", Toast.LENGTH_SHORT).show()

            //Highlight the correct button
            highlightButton(selectedAnswer, true)
        }
        else{
            Toast.makeText(this, "Wrong! Correct answer is: " +
                    currentWord.nounClass,Toast.LENGTH_SHORT).show()

            //Highlight the selected button as incorrect and show the correct button
            highlightButton(selectedAnswer, false)
            highlightButton(currentWord.nounClass, true)
        }

        updateScore()
    }


    private fun highlightButton(buttonText: String, correct: Boolean) {
        val colorRes =  if (correct) android.R.color.holo_green_light
                        else android.R.color.holo_red_light

        val buttons = listOf(optionOneButton, optionTwoButton, optionThreeButton, optionFourButton)
        for (button in buttons) {
            if (button.text.toString() == buttonText) {
                button.setBackgroundResource(colorRes)
                break
            }
        }
    }

    private fun updateScore() {
        "Score: $correctAnswers/$totalQuestions".also { scoreTextView.text = it }
    }

    private fun initaliseWordList(): List<SwahiliWord> {
        return listOf(
            // M-/WA- Class (Class 1/2) - People
            SwahiliWord("mtu", "M-/WA-" , "person"),
            SwahiliWord("mwalimu", "M-/WA-", "teacher"),
            SwahiliWord("mtoto", "M-/WA-", "child"),
            SwahiliWord("mgeni", "M-/WA-", "visitor/guest"),
            SwahiliWord("mzee", "M-/WA-", "elder"),

            // M-/MI- Class (Class 3/4) - Plants and natural things
            SwahiliWord("mti", "M-/MI-", "tree"),
            SwahiliWord("mlima", "M-/MI-", "mountain"),
            SwahiliWord("mto", "M-/MI-", "river"),
            SwahiliWord("moyo", "M-/MI-", "heart"),
            SwahiliWord("mwezi", "M-/MI-", "moon/month"),

            // JI-/MA- Class (Class 5/6) - Various objects, fruits
            SwahiliWord("jina", "JI-/MA-","name"),
            SwahiliWord("jicho", "JI-/MA-","eye"),
            SwahiliWord("tunda", "JI-/MA-", "fruit"),
            SwahiliWord("embe", "JI-/MA-","mango"),
            SwahiliWord("sikio", "JI-/MA-","ear"),

            // KI-/VI- Class (Class 7/8) - Tools, small things
            SwahiliWord("kitu", "KI-/VI-", "thing"),
            SwahiliWord("kitabu", "KI-/VI-", "book"),
            SwahiliWord("kisu", "KI-/VI-", "knife"),
            SwahiliWord("kiatu", "KI-/VI-", "shoe"),
            SwahiliWord("kikombe", "KI-/VI-", "cup"),

            // N- Class (Class 9/10) - Animals, foreign words
            SwahiliWord("ndizi", "N-", "banana"),
            SwahiliWord("nguo", "N-", "clothes"),
            SwahiliWord("simba", "N-", "lion"),
            SwahiliWord("ndege", "N-", "bird/airplane"),
            SwahiliWord("nyumba", "N-", "house"),

            // U- Class (Class 11) - Abstract concepts
            SwahiliWord("uhuru", "U-", "freedom"),
            SwahiliWord("utamaduni", "U-", "culture"),
            SwahiliWord("uzuri", "U-", "beauty"),
            SwahiliWord("ujuzi", "U-", "skill"),
            SwahiliWord("uchungu", "U-", "bitterness/pain")
        )
    }

    private fun getAllNounClasses(): List<String> {
        return listOf(
            "M-/WA-",
            "M-/MI-",
            "JI-/MA-",
            "KI-/VI-",
            "N-",
            "U-"
        )
    }



}