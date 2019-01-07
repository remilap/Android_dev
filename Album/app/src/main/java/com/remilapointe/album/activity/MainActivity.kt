package com.remilapointe.album.activity

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.view.KeyEvent
import android.view.inputmethod.EditorInfo
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.remilapointe.album.R
import com.remilapointe.album.adapater.AlbumAdapter
import com.remilapointe.album.entity.Album
import com.remilapointe.album.model.AlbumViewHolder
import com.remilapointe.album.model.AlbumViewModel
import com.remilapointe.album.tools.DbWorkerThread
import kotlinx.android.synthetic.main.activity_main.*
import java.io.Serializable

/**
 * Shows a list of Albums, with swipe-to-delete, and an input field at the top to add.
 * <p>
 * Albums are stored in a database, so swipes and additions edit the database directly, and the UI
 * is updated automatically using paging components.
 */
class MainActivity : AppCompatActivity() {

    private val viewModel by lazy(LazyThreadSafetyMode.NONE) {
        ViewModelProviders.of(this).get(AlbumViewModel::class.java)
    }

    private lateinit var mDbWorkerThread: DbWorkerThread

    private val mUiHandler = Handler()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        mDbWorkerThread = DbWorkerThread("dbWorkerThread")
        mDbWorkerThread.start()

        // Create adapter for the RecyclerView
        val adapter = AlbumAdapter { album: Album -> albumClicked(album) }
        albumList.adapter = adapter

        // Subscribe the adapter to the ViewModel, so the items in the adapter are refreshed
        // when the list changes
        viewModel.allAlbums.observe(this, Observer(adapter::submitList))

        initAddButtonListener()
        initSwipeToDelete()
    }

    private fun albumClicked(album: Album) {
        Toast.makeText(this, "Clicked: ${album.name}", Toast.LENGTH_LONG).show()

        // Launch second activity, pass album ...
        val showDetailActivityIntent = Intent(this, AlbumDetailsActivity::class.java)
        showDetailActivityIntent.putExtra(Intent.EXTRA_TEXT, album as Serializable)
        startActivity(showDetailActivityIntent)
    }

    private fun initSwipeToDelete() {
        ItemTouchHelper(object : ItemTouchHelper.Callback() {
            override fun getMovementFlags(recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder): Int =
                makeMovementFlags(0, ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT)

            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean = false

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                (viewHolder as AlbumViewHolder).album?.let {
                    viewModel.remove(it)
                }
            }
        }).attachToRecyclerView(albumList)
    }

    private fun addAlbum() {
        val newAlbum = inputText.text.trim()
        if (newAlbum.isNotEmpty()) {
            viewModel.insert(newAlbum)
            inputText.setText("")
        }
    }

    private fun initAddButtonListener() {
        addButton.setOnClickListener {
            addAlbum()
        }

        // when the user taps the "Done" button in the on screen keyboard, save the item.
        inputText.setOnEditorActionListener { _, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_DONE) {
                addAlbum()
                return@setOnEditorActionListener true
            }
            false // action that isn't DONE occurred - ignore
        }
        // When the user clicks on the button, or presses enter, save the item.
        inputText.setOnKeyListener { _, keyCode, event ->
            if (event.action == KeyEvent.ACTION_DOWN && keyCode == KeyEvent.KEYCODE_ENTER) {
                addAlbum()
                return@setOnKeyListener true
            }
            false // event that isn't DOWN or ENTER occurred - ignore
        }
    }

}
