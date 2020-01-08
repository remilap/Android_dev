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

fun Context.ArticleDetailIntent(
    article: Article?,
    produitTab: ArrayList<String>,
    coloriTab: ArrayList<String>,
    tailleTab: ArrayList<String>,
    placelogoTab: ArrayList<String>
): Intent {
    return Intent(this, ArticleAddActivity::class.java).apply {
        d("${Article.ELEM}.id= ${article?.id}, " +
                "${Article.PRODUIT_ID}= ${article?.produitId}, " +
                "${Article.COLORI_ID}= ${article?.coloriId}, " +
                "${Article.TAILLE_ID}= ${article?.tailleId}, " +
                "${Article.PLACELOGO_ID} = ${article?.placeLogoId}, " +
                "coloriTab size= ${coloriTab.size}, " +
                "colori 1= ${coloriTab[0]}")
        putExtra(EXTRA_QUERY_ARTICLE_ID, article?.id)
        putExtra(EXTRA_QUERY_ARTICLE_PRODUIT_INDEX, article?.produitId)
        putExtra(EXTRA_QUERY_ARTICLE_COLORI_INDEX, article?.coloriId)
        putExtra(EXTRA_QUERY_ARTICLE_TAILLE_INDEX, article?.tailleId)
        putExtra(EXTRA_QUERY_ARTICLE_PLACELOGO_INDEX, article?.placeLogoId)
        putStringArrayListExtra(EXTRA_QUERY_PRODUIT_LIST, produitTab)
        putStringArrayListExtra(EXTRA_QUERY_COLORI_LIST, coloriTab)
        putStringArrayListExtra(EXTRA_QUERY_TAILLE_LIST, tailleTab)
        putStringArrayListExtra(EXTRA_QUERY_PLACELOGO_LIST, placelogoTab)
    }
}

private const val EXTRA_QUERY_ARTICLE = "com.remilapointe.laser.QUERY_ARTICLE"
private const val EXTRA_QUERY_ARTICLE_ID = "com.remilapointe.laser.QUERY_ARTICLE_ID"
private const val EXTRA_QUERY_ARTICLE_PRODUIT_INDEX = "com.remilapointe.laser.QUERY_ARTICLE_PRODUIT_INDEX"
private const val EXTRA_QUERY_ARTICLE_COLORI_INDEX = "com.remilapointe.laser.QUERY_ARTICLE_COLORI_INDEX"
private const val EXTRA_QUERY_ARTICLE_TAILLE_INDEX = "com.remilapointe.laser.QUERY_ARTICLE_TAILLE_INDEX"
private const val EXTRA_QUERY_ARTICLE_PLACELOGO_INDEX = "com.remilapointe.laser.QUERY_ARTICLE_PLACELOGO_INDEX"
private const val EXTRA_QUERY_PRODUIT_LIST = "com.remilapointe.laser.QUERY_PRODUIT_LIST"
private const val EXTRA_QUERY_COLORI_LIST = "com.remilapointe.laser.QUERY_COLORI_LIST"
private const val EXTRA_QUERY_TAILLE_LIST = "com.remilapointe.laser.QUERY_TAILLE_LIST"
private const val EXTRA_QUERY_PLACELOGO_LIST = "com.remilapointe.laser.QUERY_PLACELOGO_LIST"


class ArticleAddActivity : AppCompatActivity() {

    private lateinit var tvArticleId: TextView
    private lateinit var spArticleAddProduit: Spinner
    private lateinit var spArticleAddColori: Spinner
    private lateinit var spArticleAddTaille: Spinner
    private lateinit var spArticleAddPlacelogo: Spinner
    private lateinit var edArticleAvecPrixAddPrixUHT: EditText
    private var articleProduitIndex: Int = 0
    private var articleColoriIndex: Int = 0
    private var articleTailleIndex: Int = 0
    private var articlePlaceLogoIndex: Int = 0
    private var articleProduit: String? = ""
    private var articleColori: String? = ""
    private var articleTaille: String? = ""
    private var articlePlacelogo: String? = ""
    private var articleAvecPrixPrixUHT: Double? = 0.0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.article_add_activity)
        d("onCreate begin")

        tvArticleId = findViewById(R.id.tvArticleAddId)
        val articleAvecPrixId = intent.getIntExtra(EXTRA_QUERY_ARTICLE_ID, -1)
        tvArticleId.text = if (articleAvecPrixId < 0) "new" else articleAvecPrixId.toString()

        articleProduitIndex = intent.getIntExtra(EXTRA_QUERY_ARTICLE_PRODUIT_INDEX, -1)
        val produitList = intent.getStringArrayListExtra(EXTRA_QUERY_PRODUIT_LIST)!!
        articleProduit = produitList[articleProduitIndex]
        d("articleAvecPrixProduit, Index= $articleProduitIndex, val= $articleProduit")

        spArticleAddProduit = findViewById(R.id.spArticleAddProduit)
        val adaptProduit = ArrayAdapter(this, android.R.layout.simple_spinner_item, produitList)
        adaptProduit.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spArticleAddProduit.adapter = adaptProduit
        spArticleAddProduit.setSelection(articleProduitIndex)
        //spArticleAvecPrixAddProduit.onItemSelectedListener = this

        articleColoriIndex = intent.getIntExtra(EXTRA_QUERY_ARTICLE_COLORI_INDEX, -1)
        val coloriList = intent.getStringArrayListExtra(EXTRA_QUERY_COLORI_LIST)!!
        articleColori = coloriList[articleColoriIndex]
        d("articleAvecPrixColori, Index= $articleColoriIndex, val= $articleColori")

        spArticleAddColori = findViewById(R.id.spArticleAddColori)
        val adaptColori = ArrayAdapter(this, android.R.layout.simple_spinner_item, coloriList)
        adaptColori.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spArticleAddColori.adapter = adaptColori
        spArticleAddColori.setSelection(articleColoriIndex)
        //spArticleAvecPrixAddColori.onItemSelectedListener = this

        articleTailleIndex = intent.getIntExtra(EXTRA_QUERY_ARTICLE_TAILLE_INDEX, -1)
        val tailleList = intent.getStringArrayListExtra(EXTRA_QUERY_TAILLE_LIST)!!
        articleTaille = tailleList[articleTailleIndex]
        d("articleAvecPrixTaille, Index= $articleTailleIndex, val= $articleTaille")

        spArticleAddTaille = findViewById(R.id.spArticleAddTaille)
        val adaptTaille = ArrayAdapter(this, android.R.layout.simple_spinner_item, tailleList)
        adaptTaille.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spArticleAddTaille.adapter = adaptTaille
        spArticleAddTaille.setSelection(articleTailleIndex)
        //spArticleAvecPrixAddTaille.onItemSelectedListener = this

        articlePlaceLogoIndex = intent.getIntExtra(EXTRA_QUERY_ARTICLE_PLACELOGO_INDEX, -1)
        val placeLogoList = intent.getStringArrayListExtra(EXTRA_QUERY_PLACELOGO_LIST)!!
        articlePlacelogo = placeLogoList[articlePlaceLogoIndex]
        d("articleAvecPrixPlaceLogo, Index= $articlePlaceLogoIndex, val= $articlePlacelogo")

        spArticleAddPlacelogo = findViewById(R.id.spArticleAddPlacelogo)
        val adaptPlaceLogo = ArrayAdapter(this, android.R.layout.simple_spinner_item, placeLogoList)
        adaptPlaceLogo.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spArticleAddPlacelogo.adapter = adaptPlaceLogo
        spArticleAddPlacelogo.setSelection(articlePlaceLogoIndex)
        //spArticleAvecPrixAddCPlacelogo.onItemSelectedListener = this

        edArticleAvecPrixAddPrixUHT = findViewById(R.id.edArticleAvecPrix_prixUHT)
        articleAvecPrixPrixUHT = intent.getDoubleExtra(EXTRA_REPLY_ARTICLEAVECPRIX_PRIX_UHT, 0.0)
        edArticleAvecPrixAddPrixUHT.setText(articleAvecPrixPrixUHT.toString())

        val button = findViewById<Button>(R.id.bt_save_article)
        button.setOnClickListener {
            d("click on ${Article.ELEM} save button")
            val replyIntent = Intent()
            if (TextUtils.isEmpty(tvArticleId.text)) {
                setResult(Activity.RESULT_CANCELED, replyIntent)
            } else {
                val elemTxt = tvArticleId.text.toString()
                var elem = 0
                try {
                    elem = elemTxt.toInt()
                } catch (e: Exception) {}
                d("${Article.ELEM}/${Article.PRIM_KEY} to save: $elem")
                replyIntent.putExtra(EXTRA_REPLY_ARTICLE_ID, elem)

                articleProduit = produitList[spArticleAddProduit.selectedItemPosition]
                d("produit selected, position= ${spArticleAddProduit.selectedItemPosition}, name= $articleProduit")
                replyIntent.putExtra(EXTRA_REPLY_ARTICLE_PRODUIT, articleProduit)
                articleColori = coloriList[spArticleAddColori.selectedItemPosition]
                d("colori selected, position= ${spArticleAddColori.selectedItemPosition}, name= $articleColori")
                replyIntent.putExtra(EXTRA_REPLY_ARTICLE_COLORI, articleColori)
                articleTaille = tailleList[spArticleAddTaille.selectedItemPosition]
                d("taille selected, position= ${spArticleAddTaille.selectedItemPosition}, name= $articleTaille")
                replyIntent.putExtra(EXTRA_REPLY_ARTICLE_TAILLE, articleTaille)
                articlePlacelogo = placeLogoList[spArticleAddPlacelogo.selectedItemPosition]
                d("placeLogo selected, position= ${spArticleAddPlacelogo.selectedItemPosition}, name= $articlePlacelogo")
                replyIntent.putExtra(EXTRA_REPLY_ARTICLE_PLACELOG, articlePlacelogo)
                val prixUHT = edArticleAvecPrixAddPrixUHT.text.toString().toDouble()
                replyIntent.putExtra(EXTRA_REPLY_ARTICLEAVECPRIX_PRIX_UHT, prixUHT)

                setResult(Activity.RESULT_OK, replyIntent)
            }
            this.finish()
        }
    }

    companion object {
        const val EXTRA_REPLY_ARTICLE_ID = "com.remilapointe.laser.REPLY_ARTICLE_ID"
        const val EXTRA_REPLY_ARTICLE_PRODUIT = "com.remilapointe.laser.REPLY_ARTICLE_PRODUIT"
        const val EXTRA_REPLY_ARTICLE_COLORI = "com.remilapointe.laser.REPLY_ARTICLE_COLORI"
        const val EXTRA_REPLY_ARTICLE_TAILLE = "com.remilapointe.laser.REPLY_ARTICLE_TAILLE"
        const val EXTRA_REPLY_ARTICLE_PLACELOG = "com.remilapointe.laser.REPLY_ARTICLE_PLACELOGO"
        const val EXTRA_REPLY_ARTICLEAVECPRIX_PRIX_UHT = "com.remilapointe.laser.REPLY_ARTICLEAVECPRIX_PRIX_UHT"
    }

}
