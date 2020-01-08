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

    private lateinit var placeLogoViewModel: PlaceLogoViewModel
    private lateinit var placeLogoListAdapter: PlaceLogoListAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        placeLogoViewModel = ViewModelProvider(this).get(PlaceLogoViewModel::class.java).apply {

        }
        placeLogoListAdapter = PlaceLogoListAdapter(passThroughContext) { item: PlaceLogo -> itemItemClicked(item) }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val root = inflater.inflate(R.layout.placelogo_fragment, container, false)

        val recyclerView = root.findViewById<RecyclerView>(R.id.placeLogoRecyclerView)
        recyclerView.adapter = placeLogoListAdapter
        recyclerView.layoutManager = LinearLayoutManager(passThroughContext)
        recyclerView.addItemDecoration(DividerItemDecoration(passThroughContext, DividerItemDecoration.VERTICAL))

        val swipeHandler = object : SwipeToDeleteCallBack(passThroughContext) {
            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val adapterSwipe = recyclerView.adapter as PlaceLogoListAdapter
                viewHolder.adapterPosition.let {
                    d("${PlaceLogo.ELEM} swipe position $it")
                    val item = adapterSwipe.get(it)
                    placeLogoViewModel.remove(item)
                }
            }
        }
        val itemTouchHelper = ItemTouchHelper(swipeHandler)
        itemTouchHelper.attachToRecyclerView(recyclerView)

        placeLogoViewModel.allPlaceLogos.observe(this, Observer { objs ->
            objs?.let { placeLogoListAdapter.setPlaceLogos(it) }
        })

        val fabadd: FloatingActionButton = root.findViewById(R.id.fab_add_placelogo)
        fabadd.setOnClickListener {
            //val intent = Intent(root.context, PlaceLogoAddActivity::class.java)
            startActivityForResult(activity?.PlaceLogoDetailIntent(""), newPlaceLogoActivityRequestCode)
        }

        val fabCheck = root.findViewById<FloatingActionButton>(R.id.fab_check_placelogo)
        fabCheck.setOnClickListener {
            //d("click on check, this= ${this@ColoriFragment}, this.context=" + context + ", root.context=" + root.context + ", passed context=" + passThroughContext)
            val allPlacelogos = placeLogoViewModel.getAllPlaceLogos()
            val sb = StringBuffer()
            allPlacelogos.forEach {
                sb.append(it.id).append("-").append(it.elem).append(", ")
            }
            activity!!.toast("" + allPlacelogos.size + " placelogo(s): " + sb)
        }

        return root
    }

    private fun itemItemClicked(item: PlaceLogo): View.OnClickListener {
        d("click on the ${PlaceLogo.ELEM} item ${item.elem}, launch update")
        //startActivityForResult(activity!!.PlaceLogoDetailIntent(item.elem), updatePlaceLogoActivityRequestCode)
        return View.OnClickListener {  }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, intentData: Intent?) {
        super.onActivityResult(requestCode, resultCode, intentData)
        d("onActivityResult ${PlaceLogo.ELEM}: request: $requestCode, result: $resultCode")
        if (requestCode == newPlaceLogoActivityRequestCode && resultCode == Activity.RESULT_OK) {
            intentData?.let { data ->
                val sPlaceLogo = data.getStringExtra(PlaceLogoAddActivity.EXTRA_REPLY_PLACELOGO)
                if (sPlaceLogo != null && sPlaceLogo.isNotEmpty()) {
                    d("${PlaceLogo.ELEM} to insert: $sPlaceLogo")
                    placeLogoViewModel.insert(sPlaceLogo)
                } else {
                    d("empty ${PlaceLogo.ELEM} cannot be inserted")
                }
            }
        } else if (requestCode == updatePlaceLogoActivityRequestCode && resultCode == Activity.RESULT_OK) {

        } else {
            activity!!.toast(R.string.empty_not_saved)
        }
    }

    companion object {
        const val newPlaceLogoActivityRequestCode = 21
        const val updatePlaceLogoActivityRequestCode = 22
    }

}
