package com.remilapointe.lependu

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
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
        const val MAX_ERRORS = 6
    }

    var wordToFind: String = ""
    var wordFound: CharArray = CharArray(0)
    var nbErrors = 0
    var letters: ArrayList<String> = ArrayList(1)
    lateinit var img: ImageView
    lateinit var wordTv: TextView
    lateinit var wordToFindTv: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        img = findViewById(R.id.img)
        wordTv = findViewById(R.id.wordTv)
        wordToFindTv = findViewById(R.id.wordToFindTv)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main, menu)
        return true
    }

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
    private fun wordFound(): Boolean {
        return wordToFind.contentEquals(wordFound.toString())
    }

    // Method for starting a new game
    private fun newGame() {
        nbErrors = -1
        letters.clear()
        wordToFind = nextWordToFind()
        longToast("Word to find is $wordToFind")
        wordFound = CharArray(wordToFind.length)
        for (i in 0 until wordFound.size) {
            wordFound[i] = '_'
        }
        updateImg(nbErrors)
        wordTv.text = wordFoundContent()
        wordToFindTv.text = ""
    }

    // Method updating the word found after user entered a character
    private fun enter(c: String) {
        // we update only if c has not already been entered
        if (!letters.contains(c)) {
            // we check if word to find contains c
            if (wordToFind.contains(c)) {
                // if so, we replace _ by the character c
                var index = wordToFind.indexOf(c)
                while (index >= 0) {
                    wordFound[index] = c[0]
                    index = wordToFind.indexOf(c)
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
        val builder : StringBuilder = StringBuilder()
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

    fun touchLetter(v: View) {
        if (nbErrors < MAX_ERRORS && !getString(R.string.you_win).equals(wordToFindTv.text)) {
            val letter : String = (v as Button).text.toString()
            enter(letter)
            wordTv.text = wordFoundContent()
            updateImg(nbErrors)

            // check if word is found
            if (wordFound()) {
                toast(R.string.you_win)
                wordToFindTv.text = R.string.you_win.toString()
            } else {
                if (nbErrors >= MAX_ERRORS) {
                    toast(R.string.you_lose)
                    wordToFindTv.text = R.string.word_to_find.toString().replace("#word#", wordToFind)
                }
            }
        } else {
            toast(R.string.game_is_ended)
        }
    }

}
