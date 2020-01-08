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
import com.remilapointe.laser.adapter.ProduitListAdapter
import com.remilapointe.laser.db.Produit
import com.remilapointe.laser.ui.viewmodel.ProduitViewModel
import org.jetbrains.anko.toast

class ProduitFragment(passedContext: Context) : Fragment() {

    val passThroughContext: Context = passedContext

    private lateinit var produitViewModel: ProduitViewModel
    private lateinit var produitListAdapter: ProduitListAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        produitViewModel = ViewModelProvider(this).get(ProduitViewModel::class.java).apply {

        }
        produitListAdapter = ProduitListAdapter(passThroughContext) { item: Produit -> itemItemClicked(item) }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val root = inflater.inflate(R.layout.produit_fragment, container, false)

        val recyclerView = root.findViewById<RecyclerView>(R.id.produitRecyclerView)
        recyclerView.adapter = produitListAdapter
        recyclerView.layoutManager = LinearLayoutManager(activity)
        recyclerView.addItemDecoration(DividerItemDecoration(passThroughContext, DividerItemDecoration.VERTICAL))

        val swipeHandler = object : SwipeToDeleteCallBack(passThroughContext) {
            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val adapterSwipe = recyclerView.adapter as ProduitListAdapter
                viewHolder.adapterPosition.let {
                    d("${Produit.ELEM} swipe position $it")
                    val item = adapterSwipe.get(it)
                    produitViewModel.remove(item)
                }
            }
        }
        val itemTouchHelper = ItemTouchHelper(swipeHandler)
        itemTouchHelper.attachToRecyclerView(recyclerView)

        produitViewModel.allProduits.observe(viewLifecycleOwner, Observer { objs ->
            objs?.let { produitListAdapter.setProduits(it) }
        })

        val fabAdd = root.findViewById<FloatingActionButton>(R.id.fab_add_colori)
        fabAdd.setOnClickListener {
            startActivityForResult(activity?.ProduitDetailIntent(""), newProduitActivityRequestCode)
        }

        val fabCheck = root.findViewById<FloatingActionButton>(R.id.fab_check_colori)
        fabCheck.setOnClickListener {
            //d("click on check, this= ${this@ColoriFragment}, this.context=" + context + ", root.context=" + root.context + ", passed context=" + passThroughContext)
            val allProduits = produitViewModel.getAllProduits()
            val sb = StringBuffer()
            allProduits.forEach {
                sb.append(it.id).append("-").append(it.elem).append(", ")
            }
            activity!!.toast("" + allProduits.size + " produit(s): " + sb)
        }

        return root
    }

    private fun itemItemClicked(item: Produit): View.OnClickListener {
        d("click on the ${Produit.ELEM} item ${item.id}, launch update")
        //startActivityForResult(activity!!.ColoriDetailIntent(item.elem), updateColoriActivityRequestCode)
        return View.OnClickListener {  }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, intentData: Intent?) {
        super.onActivityResult(requestCode, resultCode, intentData)
        d("onActivityResult ${Produit.ELEM}: request: $requestCode, result: $resultCode")
        if (requestCode == newProduitActivityRequestCode && resultCode == Activity.RESULT_OK) {
            intentData?.let { data ->
                val sProduit = data.getStringExtra(ProduitAddActivity.EXTRA_REPLY_PRODUIT)
                if (sProduit != null && sProduit.isNotEmpty()) {
                    d("${Produit.ELEM} to insert: $sProduit")
                    produitViewModel.insert(sProduit)
                } else {
                    d("empty ${Produit.ELEM} cannot be inserted")
                }
            }
        } else if (requestCode == updateProduitActivityRequestCode && resultCode == Activity.RESULT_OK) {

        } else {
            activity!!.toast(R.string.empty_not_saved)
        }
    }

    companion object {
        const val newProduitActivityRequestCode = 31
        const val updateProduitActivityRequestCode = 32
    }

}
