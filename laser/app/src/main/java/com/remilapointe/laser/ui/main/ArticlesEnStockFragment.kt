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
import com.remilapointe.laser.adapter.ArticlesEnStockListAdapter
import com.remilapointe.laser.db.ArticlesEnStock
import com.remilapointe.laser.ui.viewmodel.ArticlesEnStockViewModel
import org.jetbrains.anko.toast

class ArticlesEnStockFragment(passedContext: Context) : Fragment() {

    val passThroughContext: Context = passedContext

    private lateinit var articlesEnStockViewModel: ArticlesEnStockViewModel
    private lateinit var adapter: ArticlesEnStockListAdapter
    private var articlesList = arrayListOf<String>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        articlesEnStockViewModel = ViewModelProvider(this).get(ArticlesEnStockViewModel::class.java).apply {  }
        adapter = ArticlesEnStockListAdapter(passThroughContext) { item: ArticlesEnStock -> itemItemClicked(item) }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val root = inflater.inflate(R.layout.articlesenstock_fragment, container, false)

        val recyclerView = root.findViewById<RecyclerView>(R.id.articlesEnStockRecyclerView)
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(activity)
        recyclerView.addItemDecoration(
            DividerItemDecoration(
                passThroughContext,
                DividerItemDecoration.VERTICAL
            )
        )

        val swipeHandler = object : SwipeToDeleteCallBack(passThroughContext) {
            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val adapterSwipe = recyclerView.adapter as ArticlesEnStockListAdapter
                viewHolder.adapterPosition.let {
                    d("${ArticlesEnStock.ELEM} swipe position $it")
                    val item = adapterSwipe.get(it)
                    articlesEnStockViewModel.remove(item)
                }
            }
        }
        val itemTouchHelper = ItemTouchHelper(swipeHandler)
        itemTouchHelper.attachToRecyclerView(recyclerView)

        articlesEnStockViewModel.allArticlesEnStock.observe(viewLifecycleOwner, Observer { objs ->
            objs?.let { adapter.setArticlesEnStock(it) }
        })

        val fabAdd = root.findViewById<FloatingActionButton>(R.id.fab_add_articlesenstock)
        fabAdd.setOnClickListener {
            getElements()
            startActivityForResult(
                activity?.ArticlesEnStockDetailIntent(null, articlesList),
                newArticlesEnStockActivityRequestCode
            )
        }

        val fabCheck = root.findViewById<FloatingActionButton>(R.id.fab_check_articlesenstock)
        fabCheck.setOnClickListener {
            val allArticlesEnStock = articlesEnStockViewModel.getAllArticlesEnStock()
            val sb = StringBuffer()
            allArticlesEnStock.forEach {
                sb.append(it.id).append("-").append(it.nb).append(", ")
            }
            activity!!.toast("" + allArticlesEnStock.size + " ${ArticlesEnStock.ELEM}(s): " + sb)
        }

        return root
    }

    private fun getElements() {
        articlesList.clear()
        articlesEnStockViewModel.allArticlesEnStock.value!!.forEach { articlesList.add(it.articleId.toString()) }
    }

    private fun itemItemClicked(item: ArticlesEnStock): View.OnClickListener {
        d("click on the ${ArticlesEnStock.ELEM}, launch update")
        getElements()
        startActivityForResult(
            activity!!.ArticlesEnStockDetailIntent(item, articlesList),
            updateArticlesEnStockActivityRequestCode
        )
        return View.OnClickListener {  }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, intentData: Intent?) {
        super.onActivityResult(requestCode, resultCode, intentData)
        d("onActivityResult ${ArticlesEnStock.ELEM}: request: $requestCode, result: $resultCode")
        if (requestCode == newArticlesEnStockActivityRequestCode && resultCode == Activity.RESULT_OK) {
            intentData?.let { data ->
                var ok = true
//                val articlesEnStockId =
//                    data.getIntExtra(ArticlesEnStockAddActivity.EXTRA_REPLY_ARTICLESENSTOCK_ID, 0)
                val sArticlesEnStockArticle =
                    data.getStringExtra(ArticlesEnStockAddActivity.EXTRA_REPLY_ARTICLESENSTOCK_ARTICLE)
                var articlesEnStockArticleId = -1
                for (article in articlesList.withIndex()) {
                    if (article.value == sArticlesEnStockArticle) {
                        articlesEnStockArticleId = article.index // TODO not good
                        break
                    }
                }
                if (articlesEnStockArticleId >= 0) {
                    d("${ArticlesEnStock.ELEM} to insert with ${ArticlesEnStock.ARTICLE_ID}: $articlesEnStockArticleId/$sArticlesEnStockArticle")
                } else {
                    d("empty ${ArticlesEnStock.ARTICLE_ID}, ${ArticlesEnStock.ELEM} cannot be inserted")
                    ok = false
                }
                val nb = data.getIntExtra(ArticlesEnStockAddActivity.EXTRA_REPLY_ARTICLESENSTOCK_NB, 0)
                if (ok) {
                    articlesEnStockViewModel.insert(articlesEnStockArticleId, nb)
                }
            }
        } else if (requestCode == updateArticlesEnStockActivityRequestCode && resultCode == Activity.RESULT_OK) {

        } else {
            activity!!.toast(R.string.empty_not_saved)
        }
    }

    companion object {
        const val newArticlesEnStockActivityRequestCode = 51
        const val updateArticlesEnStockActivityRequestCode = 52
    }

}
