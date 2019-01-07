package com.remilapointe.album.activity

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.remilapointe.album.R
import com.remilapointe.album.entity.Album
import kotlinx.android.synthetic.main.album_details.*

class AlbumDetailsActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.album_details)

        var intentThatStartedThisActivity = intent

        if (intentThatStartedThisActivity.hasExtra(Intent.EXTRA_TEXT)) {
            var album = intentThatStartedThisActivity.extras!!.get(Intent.EXTRA_TEXT) as Album
            txt_name.text = album.name
            txt_created.text = album.created.toString()
            txt_modified.text = album.modified.toString()
            chk_pinned.isChecked = album.pinned
            chk_deleted.isChecked = album.deleted
            chk_hidden.isChecked = album.hidden
        }
    }

}
