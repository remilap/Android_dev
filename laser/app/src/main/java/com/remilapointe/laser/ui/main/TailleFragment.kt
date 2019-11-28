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
import com.remilapointe.laser.adapter.TailleListAdapter
import com.remilapointe.laser.db.Taille
import com.remilapointe.laser.ui.viewmodel.TailleViewModel
import org.jetbrains.anko.toast

class TailleFragment(passedContext: Context) : Fragment() {

    val passThroughContext: Context = passedContext

    private lateinit var itemViewModel: TailleViewModel
    private lateinit var adapter: TailleListAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        itemViewModel = ViewModelProvider(this).get(TailleViewModel::class.java).apply {

        }
        adapter = TailleListAdapter(passThroughContext) { item: Taille -> itemItemClicked(item) }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val root = inflater.inflate(R.layout.taille_fragment, container, false)

        val recyclerView = root.findViewById<RecyclerView>(R.id.tailleRecyclerView)
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(passThroughContext)
        recyclerView.addItemDecoration(DividerItemDecoration(passThroughContext, DividerItemDecoration.VERTICAL))

        val swipeHandler = object : SwipeToDeleteCallBack(passThroughContext) {
            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val adapterSwipe = recyclerView.adapter as TailleListAdapter
                viewHolder.adapterPosition.let {
                    d("$kind swipe position $it")
                    val item = adapterSwipe.get(it)
                    itemViewModel.remove(item)
                }
            }
        }
        val itemTouchHelper = ItemTouchHelper(swipeHandler)
        itemTouchHelper.attachToRecyclerView(recyclerView)

        itemViewModel.allTailles.observe(this, Observer { objs ->
            objs?.let { adapter.setStrings(it) }
        })

        val fabAdd = root.findViewById<FloatingActionButton>(R.id.fab_add_taille)
        fabAdd.setOnClickListener {
            //d("click on add coloriId, this= ${this@TailleFragment}, this.context=" + context + ", root.context=" + root.context + ", passed context=" + passThroughContext)
            startActivityForResult(activity?.TailleDetailIntent(""), newTailleActivityRequestCode)
        }

        val fabCheck = root.findViewById<FloatingActionButton>(R.id.fab_check_taille)
        fabCheck.setOnClickListener {
            //d("click on check, this= ${this@ColoriFragment}, this.context=" + context + ", root.context=" + root.context + ", passed context=" + passThroughContext)
            val allTailles = itemViewModel.getAllObjs()
            val sb = StringBuffer("Liste des elements: ")
            allTailles.forEach {
                sb.append(it.elem).append(", ")
            }
            activity!!.toast("nb colori: " + allTailles.size + ", are: " + sb)
        }

        return root
    }

    private fun itemItemClicked(item: Taille): View.OnClickListener {
        d("click on the $kind item ${item.elem}, launch update")
        //startActivityForResult(activity!!.TailleDetailIntent(item.elem), updateTailleActivityRequestCode)
        return View.OnClickListener {  }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, intentData: Intent?) {
        super.onActivityResult(requestCode, resultCode, intentData)
        d("onActivityResult $kind request: $requestCode, result: $resultCode")
        if (requestCode == newTailleActivityRequestCode && resultCode == Activity.RESULT_OK) {
            intentData?.let { data ->
                val sItem = data.getStringExtra(TailleAddActivity.EXTRA_REPLY_TAILLE)
                if (sItem != null && sItem.isNotEmpty()) {
                    d("$kind to insert: $sItem")
                    itemViewModel.insert(sItem)
                } else {
                    d("empty $kind cannot be inserted")
                }
            }
        } else if (requestCode == updateTailleActivityRequestCode && resultCode == Activity.RESULT_OK) {

        } else {
            activity!!.toast(R.string.empty_not_saved)
        }
    }

    companion object {
        const val kind = "taille"
        const val newTailleActivityRequestCode = 11
        const val updateTailleActivityRequestCode = 12
    }

}
