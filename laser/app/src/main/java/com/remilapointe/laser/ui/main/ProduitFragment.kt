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
import org.jetbrains.anko.longToast
import org.jetbrains.anko.toast

class ProduitFragment(passedContext: Context) : Fragment() {

    val passThroughContext: Context = passedContext

    private lateinit var produitViewModel: ProduitViewModel
    private lateinit var adapter: ProduitListAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        adapter = ProduitListAdapter(passThroughContext) { item: Produit -> itemItemClicked(item) }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val root = inflater.inflate(R.layout.produit_fragment, container, false)

        val recyclerView = root.findViewById<RecyclerView>(R.id.produitRecyclerView)
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(activity)
        recyclerView.addItemDecoration(DividerItemDecoration(passThroughContext, DividerItemDecoration.VERTICAL))

        val swipeHandler = object : SwipeToDeleteCallBack(passThroughContext) {
            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val adapterSwipe = recyclerView.adapter as ProduitListAdapter
                viewHolder.adapterPosition.let {
                    d("$kind swipe position $it")
                    val item = adapterSwipe.get(it)
                    produitViewModel.remove(item)
                }
            }
        }
        val itemTouchHelper = ItemTouchHelper(swipeHandler)
        itemTouchHelper.attachToRecyclerView(recyclerView)

        produitViewModel = ViewModelProvider(this).get(ProduitViewModel::class.java)
        produitViewModel.allProduits.observe(this, Observer { objs ->
            objs?.let { adapter.setProduits(it) }
        })
        produitViewModel.allColoris.observe(this, Observer { objs ->
            objs?.let { adapter.setColoris(it) }
        })
        produitViewModel.allTailles.observe(this, Observer { objs ->
            objs?.let { adapter.setTailles(it) }
        })
        produitViewModel.allPlaceLogs.observe(this, Observer { ojs ->
            ojs?.let { adapter.setPlaceLogs(it) }
        })

//        val allColoris = produitViewModel.allColoris.value
        val tabColoris = produitViewModel.allColoris.value?.toTypedArray()
        val sb = StringBuffer()
        sb.append(tabColoris?.size)
        sb.append(" coloris (")
        tabColoris?.forEach {sb.append(it.elem).append(", ")}
        sb.append("), ")
        val tabTailles = produitViewModel.allTailles.value?.toTypedArray()
        sb.append(tabTailles?.size)
        sb.append(" tailles (")
        tabTailles?.forEach {sb.append(it.elem).append(", ")}
        sb.append("), ")
        val tabPlaceLogos = produitViewModel.allPlaceLogs.value?.toTypedArray()
        sb.append(tabPlaceLogos?.size)
        sb.append(" places logo (")
        tabPlaceLogos?.forEach {sb.append(it.elem).append(", ")}
        sb.append("), ")
        activity!!.longToast(sb)
        val fabAdd = root.findViewById<FloatingActionButton>(R.id.fab_add_colori)
        fabAdd.setOnClickListener {
//            val intent = Intent(this@ProduitFragment.context, ProduitAddActivity::class.java)
            startActivityForResult(
                activity?.ProduitDetailIntent(
                    null,
                    tabColoris!!,
                    tabTailles!!,
                    tabPlaceLogos!!
                ),
                newProduitActivityRequestCode
            )
        }

        val fabCheck = root.findViewById<FloatingActionButton>(R.id.fab_check_colori)
        fabCheck.setOnClickListener {
            //d("click on check, this= ${this@ColoriFragment}, this.context=" + context + ", root.context=" + root.context + ", passed context=" + passThroughContext)
            val sb2 = StringBuffer("Liste des elements: ")
            produitViewModel.allProduits.value?.forEach {
                sb2.append(it.coloriId).append("/").append(it.tailleId).append("/").append(it.placeLogoId).append(", ")
            }
            activity!!.toast(sb2)
        }

        return root
    }

    private fun itemItemClicked(item: Produit): View.OnClickListener {
        //val intent = Intent(this@ColoriFragment.context, ColoriAddActivity::class.java)
        //intent.putExtra(ColoriAddActivity.EXTRA_QUERY_COLORI, item.elem)
        d("click on the $kind item ${item.id}, launch update")
        //startActivityForResult(activity!!.ColoriDetailIntent(item.elem), updateColoriActivityRequestCode)
        return View.OnClickListener {  }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, intentData: Intent?) {
        super.onActivityResult(requestCode, resultCode, intentData)
        d("onActivityResult $kind: request: $requestCode, result: $resultCode")
        if (requestCode == newProduitActivityRequestCode && resultCode == Activity.RESULT_OK) {
            intentData?.let { data ->
                val produitId = data.getIntExtra(ProduitAddActivity.EXTRA_REPLY_PRODUIT_ID, 0)
                val produitColoriId = data.getIntExtra(ProduitAddActivity.EXTRA_REPLY_PRODUIT_COLORI_ID, 0)
                val produitTailleId = data.getIntExtra(ProduitAddActivity.EXTRA_REPLY_PRODUIT_TAILLE_ID, 0)
                val produitPlacelogoId = data.getIntExtra((ProduitAddActivity.EXTRA_REPLY_PRODUIT_PLACELOG_ID), 0)
                if (produitId != 0) {
                    d("$kind to insert: $produitId")
                    produitViewModel.insert(produitColoriId, produitTailleId, produitPlacelogoId)
                } else {
                    d("empty $kind cannot be inserted")
                }
            }
        } else if (requestCode == updateColoriActivityRequestCode && resultCode == Activity.RESULT_OK) {

        } else {
            activity!!.toast(R.string.empty_not_saved)
        }
    }

    companion object {
        const val kind = "produit"
        const val newProduitActivityRequestCode = 31
        const val updateColoriActivityRequestCode = 32
    }

}
