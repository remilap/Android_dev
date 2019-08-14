package com.remilapointe.lependu

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import org.jetbrains.anko.longToast
import org.jetbrains.anko.toast
import java.lang.StringBuilder
import java.util.*
import kotlin.collections.ArrayList


class MainActivity : AppCompatActivity() {

    companion object {
        val WORDS = listOf("ABSTRACT", "ASSERT", "BOOLEAN", "BREAK", "BYTE",
            "CASE", "CATCH", "CHAR", "CLASS", "CONST",
            "CONTINUE", "DEFAULT", "DOUBLE", "DO", "ELSE",
            "ENUM", "FALSE", "EXTENDS", "FINAL", "FINALLY",
            "FLOAT", "FOR", "GOTO", "IF", "IMPLEMENTS",
            "IMPORT", "INSTANCEOF", "INT", "INTERFACE",
            "LONG", "NATIVE", "NEW", "NULL", "PACKAGE",
            "PRIVATE", "PROTECTED", "PUBLIC", "RETURN",
            "SHORT", "STATIC", "STRICTFP", "SUPER", "SWITCH",
            "SYNCHRONIZED", "THIS", "THROW", "THROWS",
            "TRANSIENT", "TRUE", "TRY", "VOID", "VOLATILE", "WHILE")
        val RANDOM = Random()
        // Max errors before user lose
        const val MAX_ERRORS = 7
        const val TAG = "PENDU"
    }

    private var wordToFind: String = ""
    private var wordFound: CharArray = CharArray(0)
    private var nbErrors = 0
    private var letters: ArrayList<String> = ArrayList(1)
    private lateinit var img: ImageView
    private lateinit var wordTv: TextView
    private lateinit var wordToFindTv: TextView

    @ExperimentalStdlibApi
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        img = findViewById(R.id.img)
        wordTv = findViewById(R.id.wordTv)
        wordToFindTv = findViewById(R.id.wordToFindTv)
        newGame()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main, menu)
        return true
    }

    @ExperimentalStdlibApi
    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        if (item!!.itemId == R.id.new_game) {
            newGame()
        }
        return super.onOptionsItemSelected(item)
    }

    // Method returning randomly next word to find
    private fun nextWordToFind(): String {
        return WORDS[RANDOM.nextInt(WORDS.size)]
    }

    // Method returning trus if word is found by user
    @ExperimentalStdlibApi
    private fun wordFound(): Boolean {
        Log.d(TAG, "wordToFind: $wordToFind, wordFound: ${wordFound.concatToString()}")
        return wordToFind.contentEquals(wordFound.concatToString())
    }

    // Method for starting a new game
    @ExperimentalStdlibApi
    private fun newGame() {
        nbErrors = 1
        Log.d(TAG, "01- size of letters: ${letters.size}")
        letters.clear()
        Log.d(TAG, "02- size of letters: ${letters.size}")
        wordToFind = nextWordToFind()
        Log.d(TAG, "Word to find is $wordToFind")
        wordFound = CharArray(wordToFind.length)
        for (i in 0 until wordFound.size) {
            wordFound[i] = '_'
        }
        Log.d(TAG, "03- wordFound: ${wordFound.concatToString()}")
        updateImg(nbErrors)
        wordTv.text = wordFoundContent()
        wordToFindTv.text = ""
    }

    // Method updating the word found after user entered a character
    private fun enter(c: String) {
        // we update only if c has not already been entered
        if (!letters.contains(c)) {
            Log.d(TAG, "04- letters does not contain $c")
            // we check if word to find contains c
            if (wordToFind.contains(c)) {
                Log.d(TAG, "05- wordToFind contains $c")
                // if so, we replace _ by the character c
                var index = wordToFind.indexOf(c)
                while (index >= 0) {
                    wordFound[index] = c[0]
                    index = wordToFind.indexOf(c, index + 1)
                }
            } else {
                // c not in the word to find => error
                nbErrors++
                toast(R.string.try_an_other)
            }
            // c is now a letter entered
            letters.add(c)
        } else {
            toast(R.string.letter_already_entered)
        }
    }

    // Method returning the state of the word found by the user until by now
    private fun wordFoundContent(): String {
        val builder = StringBuilder()
        for (i in 0 until wordFound.size) {
            builder.append(wordFound[i])
            if (i < wordFound.size - 1) {
                builder.append(" ")
            }
        }
        return builder.toString()
    }

    private fun updateImg(play: Int) {
        val resImg = resources.getIdentifier("hangman_$play", "drawable", packageName)
        img.setImageResource(resImg)
    }

    @ExperimentalStdlibApi
    fun touchLetter(v: View) {
        if (nbErrors < MAX_ERRORS && !getString(R.string.you_win).equals(wordToFindTv.text)) {
            val letter : String = (v as Button).text.toString()
            enter(letter)
            wordTv.text = wordFoundContent()
            updateImg(nbErrors)

            // check if word is found
            if (wordFound()) {
                toast(R.string.you_win)
                wordToFindTv.setText(R.string.you_win)
            } else {
                if (nbErrors >= MAX_ERRORS) {
                    toast(R.string.you_lose)
                    wordToFindTv.text = resources.getText(R.string.word_to_find).toString().replace("#word#", wordToFind)
                }
            }
        } else {
            toast(R.string.game_is_ended)
        }
    }

}
