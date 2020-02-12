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
import com.remilapointe.laser.adapter.ColoriListAdapter
import com.remilapointe.laser.db.Colori
import com.remilapointe.laser.ui.viewmodel.LaserViewModel
import org.jetbrains.anko.toast

class ColoriFragment(passedContext: Context) : Fragment(), ColoriListAdapter.OnSaveItems {

    val passThroughContext: Context = passedContext

    private lateinit var laserViewModel: LaserViewModel
    private lateinit var adapter: ColoriListAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        laserViewModel = ViewModelProvider(this).get(LaserViewModel::class.java).apply {

        }
//        adapter = ColoriListAdapter(passThroughContext) { item: Colori -> itemItemClicked(item) }
        adapter = ColoriListAdapter(passThroughContext, this) { item: Colori -> itemItemClicked(item) }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val root = inflater.inflate(R.layout.colori_fragment, container, false)

        val recyclerView = root.findViewById<RecyclerView>(R.id.coloriRecyclerView)
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(activity)
        recyclerView.addItemDecoration(DividerItemDecoration(passThroughContext, DividerItemDecoration.VERTICAL))

        val swipeHandler = object : SwipeToDeleteCallBack(passThroughContext) {
            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val adapterSwipe = recyclerView.adapter as ColoriListAdapter
                viewHolder.adapterPosition.let {
                    d("${Colori.ELEM} swipe position $it")
                    val item = adapterSwipe.get(it)
                    laserViewModel.removeColori(item)
                }
            }
        }
        val itemTouchHelper = ItemTouchHelper(swipeHandler)
        itemTouchHelper.attachToRecyclerView(recyclerView)

        laserViewModel.allColoris.observe(viewLifecycleOwner, Observer { objs ->
            objs?.let { adapter.setColoris(it) }
        })

        val fabAdd = root.findViewById<FloatingActionButton>(R.id.fab_add_colori)
        fabAdd.setOnClickListener {
            //d("click on add ${Colori.ELEM}, this= ${this@ColoriFragment}, this.context=" + context + ", root.context=" + root.context + ", passed context=" + passThroughContext)
            //val intent = Intent(this@ColoriFragment.context, ColoriAddActivity::class.java)
            startActivityForResult(activity?.ColoriDetailIntent(""), newColoriActivityRequestCode)
        }

        val fabCheck = root.findViewById<FloatingActionButton>(R.id.fab_check_colori)
        fabCheck.setOnClickListener {
            //d("click on check, this= ${this@ColoriFragment}, this.context=" + context + ", root.context=" + root.context + ", passed context=" + passThroughContext)
            val allColoris = laserViewModel.getAllColoris()
            val sb = StringBuffer()
            allColoris.forEach {
                sb.append(it.id).append("-").append(it.elem).append(", ")
            }
            activity!!.toast("" + allColoris.size + " colori(s): " + sb)
        }

        return root
    }

    private fun itemItemClicked(item: Colori): View.OnClickListener {
        //val intent = Intent(this@ColoriFragment.context, ColoriAddActivity::class.java)
        //intent.putExtra(ColoriAddActivity.EXTRA_QUERY_COLORI, item.elem)
        d("click on the ${Colori.ELEM} item ${item.elem}, launch update")
        //startActivityForResult(activity!!.ColoriDetailIntent(item.elem), updateColoriActivityRequestCode)
        return View.OnClickListener {  }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, intentData: Intent?) {
        super.onActivityResult(requestCode, resultCode, intentData)
        d("onActivityResult ${Colori.ELEM}: request: $requestCode, result: $resultCode")
        if (requestCode == newColoriActivityRequestCode && resultCode == Activity.RESULT_OK) {
            intentData?.let { data ->
                val sColori = data.getStringExtra(ColoriAddActivity.EXTRA_REPLY_COLORI)
                if (sColori != null && sColori.isNotEmpty()) {
                    d("${Colori.ELEM} to insert: $sColori")
                    laserViewModel.insertColori(sColori)
                } else {
                    d("empty ${Colori.ELEM} cannot be inserted")
                }
            }
        } else if (requestCode == updateColoriActivityRequestCode && resultCode == Activity.RESULT_OK) {

        } else {
            activity!!.toast(R.string.empty_not_saved)
        }
    }

    override fun saveColori(position: Int, vararg items: Colori?) {
        items.forEach {
            laserViewModel.updateColori(it!!)
        }
    }

    companion object {
        const val newColoriActivityRequestCode = 1
        const val updateColoriActivityRequestCode = 2
    }

}
