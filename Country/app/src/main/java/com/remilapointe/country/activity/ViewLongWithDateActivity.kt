package com.remilapointe.country.activity

import android.app.Application
import android.os.Bundle
import android.os.PersistableBundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.remilapointe.country.R
import com.remilapointe.country.adapter.LongWithDateAdapter
import com.remilapointe.country.entity.LongWithDate
import com.remilapointe.country.model.LongWithDateViewModel
import kotlinx.android.synthetic.main.longwithdate_list.*
import org.jetbrains.anko.toast

class ViewLongWithDateActivity : AppCompatActivity() {

    private val viewModel by lazy(LazyThreadSafetyMode.NONE) {
        ViewModelProviders.of(this).get(LongWithDateViewModel::class.java)
    }


    override fun onCreate(savedInstanceState: Bundle?, persistentState: PersistableBundle?) {
        super.onCreate(savedInstanceState, persistentState)
        setContentView(R.layout.longwithdate_short_item)

        // Create adapter for the RecyclerView
        val adapter = LongWithDateAdapter { longWithDate: LongWithDate -> longWithDateClicked(longWithDate) }
        longWithDateList.adapter = adapter

        // Subscribe the adapter to the ViewModel, so the items in the adapter are refreshed
        // when the list changes
        viewModel.allLongWithDate.observe(this, Observer(adapter::submitList))
    }

    private fun longWithDateClicked(longWithDate: LongWithDate) {
        this.applicationContext.toast("Clicked on ${longWithDate.id}")
    }
}
