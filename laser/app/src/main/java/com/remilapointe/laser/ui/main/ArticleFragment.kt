package com.remilapointe.laser.ui.main

import android.app.Activity
import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.SystemClock
import android.util.Base64.CRLF
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.log4k.d
import com.remilapointe.laser.ConfigurationActivity
import com.remilapointe.laser.R
import com.remilapointe.laser.adapter.ArticleListAdapter
import com.remilapointe.laser.db.Article
import com.remilapointe.laser.ui.viewmodel.LaserViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.jetbrains.anko.toast

class ArticleFragment(passedContext: Context) : Fragment() {

    val passThroughContext: Context = passedContext

    private lateinit var laserViewModel: LaserViewModel
    private lateinit var adapter: ArticleListAdapter
    private var produitsList = arrayListOf<String>()
    private var colorisList = arrayListOf<String>()
    private var taillesList = arrayListOf<String>()
    private var placelogosList = arrayListOf<String>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        laserViewModel = ViewModelProvider(this).get(LaserViewModel::class.java)
        adapter = ArticleListAdapter(passThroughContext, laserViewModel) { item: Article -> itemItemClicked(item) }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val root = inflater.inflate(R.layout.article_fragment, container, false)

        val recyclerView = root.findViewById<RecyclerView>(R.id.articleRecyclerView)
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(activity)
        recyclerView.addItemDecoration(DividerItemDecoration(passThroughContext, DividerItemDecoration.VERTICAL))

        val swipeHandler = object : SwipeToDeleteCallBack(passThroughContext) {
            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val adapterSwipe = recyclerView.adapter as ArticleListAdapter
                viewHolder.adapterPosition.let {
                    d("${Article.ELEM} swipe position $it")
                    val item = adapterSwipe.get(it)
                    laserViewModel.removeArticle(item)
                }
            }
        }
        val itemTouchHelper = ItemTouchHelper(swipeHandler)
        itemTouchHelper.attachToRecyclerView(recyclerView)

        laserViewModel.allArticles.observe(viewLifecycleOwner, Observer { it?.let { adapter.setArticles(it) } })
        laserViewModel.allProduits.observe(viewLifecycleOwner, Observer { it?.let { adapter.setProduits(it) } })
        laserViewModel.allColoris.observe(viewLifecycleOwner, Observer { it?.let { adapter.setColoris(it) } })
        laserViewModel.allTailles.observe(viewLifecycleOwner, Observer { it?.let { adapter.setTailles(it) } })
        laserViewModel.allPlaceLogos.observe(viewLifecycleOwner, Observer { it?.let { adapter.setPlaceLogo(it) } })
        SystemClock.sleep(1000)

        val btAddAllArticles = root.findViewById<Button>(R.id.btAddAllArticles)
        btAddAllArticles.setOnClickListener {
            val viewmodelCoroutineScope = CoroutineScope(Dispatchers.IO)
            viewmodelCoroutineScope.launch {
                withContext(Dispatchers.IO) {
                    laserViewModel.getAllProduits().forEach { produit ->
                        laserViewModel.getAllColoris().forEach { colori ->
                            laserViewModel.getAllTailles().forEach { taille ->
                                laserViewModel.getAllPlaceLogos().forEach { placeLogo ->
                                    if (laserViewModel.getArticleByCompIds(produit.id, colori.id, taille.id, placeLogo.id) == null) {
                                        laserViewModel.insertArticle(produit.id, colori.id, taille.id, placeLogo.id)
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }

        val fabAddArticle = root.findViewById<FloatingActionButton>(R.id.fabAddArticle)
        fabAddArticle.setOnClickListener {
            //d("click on add $kind, this= ${this@ColoriFragment}, this.context=" + context + ", root.context=" + root.context + ", passed context=" + passThroughContext)
            //val intent = Intent(this@ColoriFragment.context, ColoriAddActivity::class.java)
            getElements()
            startActivityForResult(
                activity?.ArticleDetailIntent(null, "", "", "", "", produitsList, colorisList, taillesList, placelogosList),
                newArticleActivityRequestCode
            )
        }

        val fabCheckArticle = root.findViewById<FloatingActionButton>(R.id.fabCheckArticle)
        fabCheckArticle.setOnClickListener {
            //d("click on check, this= ${this@ColoriFragment}, this.context=" + context + ", root.context=" + root.context + ", passed context=" + passThroughContext)
            val allArticles = laserViewModel.getAllArticles()
            val sb = StringBuffer()
            allArticles.forEach {
                sb.append(it.id).append("-").append(it.produitId).append(", ")
            }
            activity!!.toast("" + allArticles.size + " ${Article.ELEM}(s): " + sb)
        }

        return root
    }

    private fun getElements() {
        produitsList = laserViewModel.getAllProduitsString()
        colorisList = laserViewModel.getAllColorisString()
        taillesList = laserViewModel.getAllTaillesString()
        placelogosList = laserViewModel.getAllPlaceLogosString()
    }

    private fun itemItemClicked(item: Article): View.OnClickListener {
        //val intent = Intent(this@ColoriFragment.context, ColoriAddActivity::class.java)
        //intent.putExtra(ColoriAddActivity.EXTRA_QUERY_COLORI, item.elem)
        d("click on the ${Article.ELEM} ${Article.PRODUIT_ID}: ${item.produitId}, launch update")
        getElements()
//        startActivityForResult(
//            activity!!.ArticleDetailIntent(item, produitsList, colorisList, taillesList, placelogosList),
//            updateArticleActivityRequestCode
//        )
        return View.OnClickListener {  }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, intentData: Intent?) {
        super.onActivityResult(requestCode, resultCode, intentData)
        d("onActivityResult ${Article.ELEM}: request: $requestCode, result: $resultCode")
        if (requestCode == newArticleActivityRequestCode && resultCode == Activity.RESULT_OK) {
            intentData?.let { data ->
                var ok = true
                val produit = data.getStringExtra(ArticleAddActivity.EXTRA_REPLY_ARTICLE_PRODUIT)
                var produitId = -1
                if (produit != null && produit.isNotEmpty()) {
                    produitId = laserViewModel.getProduitId(produit)
                    d("${Article.ELEM} to insert with ${Article.PRODUIT_ID}: $produitId/$produit")
                } else {
                    d("empty ${Article.PRODUIT_ID}, ${Article.ELEM} cannot be inserted")
                    ok = false
                }
                val colori = data.getStringExtra(ArticleAddActivity.EXTRA_REPLY_ARTICLE_COLORI)
                var coloriId = -1
                if (colori != null && colori.isNotEmpty()) {
                    coloriId = laserViewModel.getColoriId(colori)
                    d("${Article.ELEM} to insert with ${Article.COLORI_ID}: $coloriId/$colori")
                } else {
                    d("empty ${Article.COLORI_ID}, ${Article.ELEM} cannot be inserted")
                    ok = false
                }
                val taille = data.getStringExtra(ArticleAddActivity.EXTRA_REPLY_ARTICLE_TAILLE)
                var tailleId = -1
                if (taille != null && taille.isNotEmpty()) {
                    tailleId = laserViewModel.getTailleId(taille)
                    d("${Article.ELEM} to insert with ${Article.TAILLE_ID}: $tailleId/$taille")
                } else {
                    d("empty ${Article.TAILLE_ID}, ${Article.ELEM} cannot be inserted")
                    ok = false
                }
                val placeLogo = data.getStringExtra(ArticleAddActivity.EXTRA_REPLY_ARTICLE_PLACELOG)
                var placeLogoId = -1
                if (placeLogo != null && placeLogo.isNotEmpty()) {
                    placeLogoId = laserViewModel.getPlaceLogoId(placeLogo)
                    d("${Article.ELEM} to insert with ${Article.PLACELOGO_ID}: $placeLogoId/$placeLogo")
                } else {
                    d("empty ${Article.PLACELOGO_ID}, ${Article.ELEM} cannot be inserted")
                    ok = false
                }
//                val prixUHT = data.getDoubleExtra(ArticleAddActivity.EXTRA_REPLY_ARTICLEAVECPRIX_PRIX_UHT, 0.0)
                if (ok) {
                    laserViewModel.insertArticle(produitId, coloriId, tailleId, placeLogoId)
                }
            }
//        } else if (requestCode == updateArticleActivityRequestCode && resultCode == Activity.RESULT_OK) {

        } else {
            val txt = intentData?.getStringExtra(ArticleAddActivity.EXTRA_REPLY_ARTICLE_MESSAGE)
            val builder = AlertDialog.Builder(activity)
            builder.setTitle(R.string.article_non_cree)
            builder.setMessage(txt + CRLF + getString(R.string.directly_goto_config))
            builder.setPositiveButton(android.R.string.ok) { dialog, _ ->
                activity!!.startActivity(Intent(activity, ConfigurationActivity::class.java))
                dialog.dismiss()
            }
            builder.setNegativeButton(android.R.string.cancel) { dialog, _ ->
                dialog.cancel()
            }
            builder.show()
//            activity!!.longToast(txt!!)
        }
    }

    companion object {
        const val newArticleActivityRequestCode = 41
        const val updateArticleActivityRequestCode = 42
    }

}
