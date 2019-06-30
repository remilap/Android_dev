package com.remilapointe.roomwordsample.ui

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.appcompat.widget.Toolbar
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.remilapointe.roomwordsample.*
import com.remilapointe.roomwordsample.db.Phrase
import com.remilapointe.roomwordsample.db.StringToListConverter
import com.remilapointe.roomwordsample.viewmodel.PhraseViewModel

class MainActivity : AppCompatActivity() {

    private lateinit var phraseViewModel: PhraseViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val toolbar = findViewById<Toolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)

        val recyclerView = findViewById<RecyclerView>(R.id.phraseRecyclerView)
        val adapter = PhraseListAdapter(this) { phrase: Phrase -> phraseItemClicked(phrase) }
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.addItemDecoration(DividerItemDecoration(this, DividerItemDecoration.VERTICAL))

        val swipeHandler = object : SwipeToDeleteCallBack(this) {
            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val adapterSwipe = recyclerView.adapter as PhraseListAdapter
                adapterSwipe.removePhrase(viewHolder.adapterPosition)
            }
        }
        val itemTouchHelper = ItemTouchHelper(swipeHandler)
        itemTouchHelper.attachToRecyclerView(recyclerView)

        // Get a new or existing ViewModel from the ViewModelProvider.
        phraseViewModel = ViewModelProviders.of(this).get(PhraseViewModel::class.java)

        // Add an observer on the LiveData returned by getAlphabetizedWords.
        // The onChanged() method fires when the observed data changes and the activity is
        // in the foreground.
        phraseViewModel.allPhrases.observe(this, Observer { phrases ->
            // Update the cached copy of the words in the adapter.
            phrases?.let { adapter.setPhrases(it) }
        })

        val fabAddPhrase = findViewById<FloatingActionButton>(R.id.fab_add_phrase)
        fabAddPhrase.setOnClickListener {
            val intent = Intent(this@MainActivity, PhraseAddActivity::class.java)
            startActivityForResult(intent, newPhraseActivityRequestCode)
        }

        val fabGotoWordsList = findViewById<FloatingActionButton>(R.id.fab_goto_word)
        fabGotoWordsList.setOnClickListener {
            val intent = Intent(this@MainActivity, WordListActivity::class.java)
            startActivity(intent)
        }
    }

    private fun phraseItemClicked(phrase: Phrase): View.OnClickListener {
        val intent = Intent(this@MainActivity, PhraseAddActivity::class.java)
        intent.putExtra(PhraseAddActivity.EXTRA_QUERY_PHRASE, StringToListConverter.wordsListToString(phrase.mots))
        startActivity(intent)
        return View.OnClickListener {  }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, intentData: Intent?) {
        super.onActivityResult(requestCode, resultCode, intentData)

        if (requestCode == newPhraseActivityRequestCode && resultCode == Activity.RESULT_OK) {
            intentData?.let { data ->
                val sPhrase = data.getStringExtra(PhraseAddActivity.EXTRA_REPLY_PHRASE)
                phraseViewModel.insert(sPhrase)
            }
        } else if (requestCode == updatePhraseActivityRequestCode && resultCode == Activity.RESULT_OK) {
        } else {
            Toast.makeText(applicationContext, R.string.empty_word_not_saved, Toast.LENGTH_LONG).show()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        return when (item.itemId) {
            R.id.action_settings -> true
            else -> super.onOptionsItemSelected(item)
        }
    }

    companion object {
        const val newPhraseActivityRequestCode = 1
        const val updatePhraseActivityRequestCode = 2
    }

}
