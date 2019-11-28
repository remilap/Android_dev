package com.remilapointe.laser.ui.main

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.log4k.d
import com.remilapointe.laser.R
import com.remilapointe.laser.adapter.PlaceLogoListAdapter
import com.remilapointe.laser.db.PlaceLogo
import com.remilapointe.laser.ui.viewmodel.PlaceLogoViewModel
import org.jetbrains.anko.toast

class PlaceLogoFragment(passedContext: Context) : Fragment() {

    val passThroughContext: Context = passedContext

    private lateinit var itemViewModel: PlaceLogoViewModel
    private lateinit var adapter: PlaceLogoListAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        itemViewModel = ViewModelProvider(this).get(PlaceLogoViewModel::class.java).apply {

        }
        adapter = PlaceLogoListAdapter(passThroughContext) { item: PlaceLogo -> itemItemClicked(item) }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val root = inflater.inflate(R.layout.placelogo_fragment, container, false)

        val recyclerView = root.findViewById<RecyclerView>(R.id.placeLogoRecyclerView)
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(passThroughContext)
        recyclerView.addItemDecoration(DividerItemDecoration(passThroughContext, DividerItemDecoration.VERTICAL))

        val swipeHandler = object : SwipeToDeleteCallBack(passThroughContext) {
            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val adapterSwipe = recyclerView.adapter as PlaceLogoListAdapter
                viewHolder.adapterPosition.let {
                    d("$kind swipe position $it")
                    val item = adapterSwipe.get(it)
                    itemViewModel.remove(item)
                }
            }
        }
        val itemTouchHelper = ItemTouchHelper(swipeHandler)
        itemTouchHelper.attachToRecyclerView(recyclerView)

        itemViewModel.allPlaceLogos.observe(this, Observer { objs ->
            objs?.let { adapter.setStrings(it) }
        })

        val fab: FloatingActionButton = root.findViewById(R.id.fab_add_placelogo)
        fab.setOnClickListener {
            //val intent = Intent(root.context, PlaceLogoAddActivity::class.java)
            startActivityForResult(activity?.PlaceLogoDetailIntent(""), newPlaceLogoActivityRequestCode)
        }

        return root
    }

    private fun itemItemClicked(item: PlaceLogo): View.OnClickListener {
        d("click on the $kind item ${item.elem}, launch update")
        //startActivityForResult(activity!!.PlaceLogoDetailIntent(item.elem), updatePlaceLogoActivityRequestCode)
        return View.OnClickListener {  }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, intentData: Intent?) {
        super.onActivityResult(requestCode, resultCode, intentData)
        d("onActivityResult $kind: request: $requestCode, result: $resultCode")
        if (requestCode == newPlaceLogoActivityRequestCode && resultCode == Activity.RESULT_OK) {
            intentData?.let { data ->
                val sItem = data.getStringExtra(PlaceLogoAddActivity.EXTRA_REPLY_PLACELOGO)
                if (sItem != null && sItem.isNotEmpty()) {
                    d("$kind to insert: $sItem")
                    itemViewModel.insert(sItem)
                } else {
                    d("empty $kind cannot be inserted")
                }
            }
        } else if (requestCode == updatePlaceLogoActivityRequestCode && resultCode == Activity.RESULT_OK) {

        } else {
            activity!!.toast(R.string.empty_not_saved)
        }
    }

    companion object {
        const val kind = "placelogo"
        const val newPlaceLogoActivityRequestCode = 21
        const val updatePlaceLogoActivityRequestCode = 22
    }

}
