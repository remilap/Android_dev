package com.remilapointe.laser.ui.main

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.log4k.d
import com.remilapointe.laser.R
import com.remilapointe.laser.db.Article
import com.remilapointe.laser.db.ArticlesEnStock

fun Context.ArticlesEnStockDetailIntent(
    articlesEnStock: ArticlesEnStock?,
    articlesTab: ArrayList<String>
): Intent {
    return Intent(this, ArticlesEnStockAddActivity::class.java).apply {
        d("${ArticlesEnStock.ELEM}.id= ${articlesEnStock?.id}, " +
        "${ArticlesEnStock.ARTICLE_ID}= ${articlesEnStock?.articleId}, " +
        "${ArticlesEnStock.NB}= ${articlesEnStock?.nb}, " +
        "articlesTab size= ${articlesTab.size}, " +
        "elem 1= ${articlesTab[0]}")
        putExtra(EXTRA_QUERY_ARTICLESENSTOCK_ID, articlesEnStock?.id)
        putExtra(EXTRA_QUERY_ARTICLESENSTOCK_ARTICLE_INDEX, articlesEnStock?.articleId)
        putExtra(EXTRA_QUERY_ARTICLESENSTOCK_NB, articlesEnStock?.nb)
        putStringArrayListExtra(EXTRA_QUERY_ARTICLES_LIST, articlesTab)
    }
}
private const val EXTRA_QUERY_ARTICLESENSTOCK = "com.remilapointe.laser.QUERY_ARTICLESENSTOCK"
private const val EXTRA_QUERY_ARTICLESENSTOCK_ID = "com.remilapointe.laser.QUERY_ARTICLESENSTOCK_ID"
private const val EXTRA_QUERY_ARTICLESENSTOCK_ARTICLE_INDEX = "com.remilapointe.laser.QUERY_ARTICLESENSTOCK_ARTICLE_INDEX"
private const val EXTRA_QUERY_ARTICLESENSTOCK_NB = "com.remilapointe.laser.QUERY_ARTICLESENSTOCK_NB"
private const val EXTRA_QUERY_ARTICLES_LIST = "com.remilapointe.laser.QUERY_ARTICLES_LIST"

class ArticlesEnStockAddActivity : AppCompatActivity() {

    private lateinit var tvArticlesEnStockId: TextView
    private lateinit var edQtArticleRef: EditText
    private lateinit var spArticlesEnStockAddArticle: Spinner
    private lateinit var edArticlesEnStockNb: EditText
    private var qtArticleRef: String? = ""
    private var articlesEnStockArticleIndex: Int = 0
    private var articlesEnStockArticle: String? = ""
    private var articlesEnStockNb: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.articlesenstock_add_activity)
        d("onCreate begin")

        tvArticlesEnStockId = findViewById(R.id.tvArticlesEnStockAddId)
        val articlesEnStockId = intent.getIntExtra(EXTRA_QUERY_ARTICLESENSTOCK_ID, -1)
        tvArticlesEnStockId.text = if (articlesEnStockId < 0) "new" else articlesEnStockId.toString()

        articlesEnStockArticleIndex = intent.getIntExtra(EXTRA_QUERY_ARTICLESENSTOCK_ARTICLE_INDEX, 0)
        val articlesList = intent.getStringArrayListExtra(EXTRA_QUERY_ARTICLES_LIST)
        articlesEnStockArticle = articlesList!![articlesEnStockArticleIndex]
        d("articlesEnStockArticle, Index= $articlesEnStockArticleIndex, val= $articlesEnStockArticle")

        spArticlesEnStockAddArticle = findViewById(R.id.spArticlesEnStockAddArticle)
        val adaptArticle = ArrayAdapter(this, android.R.layout.simple_spinner_item, articlesList)
        adaptArticle.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spArticlesEnStockAddArticle.adapter = adaptArticle
        spArticlesEnStockAddArticle.setSelection(articlesEnStockArticleIndex)

        edArticlesEnStockNb = findViewById(R.id.edArticlesEnStockAddNb)
        articlesEnStockNb = intent.getIntExtra(EXTRA_QUERY_ARTICLESENSTOCK_NB, 0)
        edArticlesEnStockNb.setText(articlesEnStockNb)

        val button = findViewById<Button>(R.id.bt_save_articlesenstock)
        button.setOnClickListener {
            d("click on ${ArticlesEnStock.ELEM} save button")
            val replyIntent = Intent()
            if (TextUtils.isEmpty(tvArticlesEnStockId.text)) {
                setResult(Activity.RESULT_CANCELED, replyIntent)
            } else {
                val elemTxt = tvArticlesEnStockId.text.toString()
                var elem = 0
                try {
                    elem = elemTxt.toInt()
                } catch (e: Exception) {}
                d("${ArticlesEnStock.ELEM}/${ArticlesEnStock.PRIM_KEY} to save: $elem")
                replyIntent.putExtra(EXTRA_REPLY_ARTICLESENSTOCK_ID, elem)

                articlesEnStockArticle = articlesList[spArticlesEnStockAddArticle.selectedItemPosition]
                d("${Article.ELEM} selected, position= ${spArticlesEnStockAddArticle.selectedItemPosition}, name= $articlesEnStockArticle")
                replyIntent.putExtra(EXTRA_REPLY_ARTICLESENSTOCK_ARTICLE, articlesEnStockArticle)

                val articlesEnStockNb = edArticlesEnStockNb.text
                replyIntent.putExtra(EXTRA_REPLY_ARTICLESENSTOCK_NB, articlesEnStockNb)

                setResult(Activity.RESULT_OK, replyIntent)
            }
            this.finish()
        }
    }

    companion object {
        const val EXTRA_REPLY_ARTICLESENSTOCK_ID = "com.remilapointe.laser.REPLY_ARTICLESENSTOCK_ID"
        const val EXTRA_REPLY_ARTICLESENSTOCK_ARTICLE = "com.remilapointe.laser.REPLY_ARTICLESENSTOCK_ARTICLE"
        const val EXTRA_REPLY_ARTICLESENSTOCK_NB = "com.remilapointe.laser.REPLY_ARTICLESENSTOCK_NB"
    }

}
