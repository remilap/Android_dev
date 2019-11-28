package com.remilapointe.laser.ui.main

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.log4k.d
import com.remilapointe.laser.R
import com.remilapointe.laser.db.*
import com.remilapointe.laser.ui.viewmodel.ProduitViewModel
import kotlinx.android.synthetic.main.produit_add_activity.*

fun Context.ProduitDetailIntent(
    produit: Produit?,
    coloriTab: Array<Colori>,
    tailleTab: Array<Taille>,
    placelogoTab: Array<PlaceLogo>
): Intent {
    return Intent(this, ProduitAddActivity::class.java).apply {
        putExtra(EXTRA_QUERY_PRODUIT_ID, produit?.id)
        putExtra(EXTRA_QUERY_PRODUIT_COLORI_ID, produit?.coloriId)
        putExtra(EXTRA_QUERY_PRODUIT_TAILLE_ID, produit?.tailleId)
        putExtra(EXTRA_QUERY_PRODUIT_PLACELOGO_ID, produit?.placeLogoId)
        putExtra(EXTRA_QUERY_COLORI_LIST, coloriTab)
        putExtra(EXTRA_QUERY_TAILLE_LIST, tailleTab)
        putExtra(EXTRA_QUERY_PLACELOGO_LIST, placelogoTab)
    }
}

private const val EXTRA_QUERY_PRODUIT_ID = "com.remilapointe.laser.QUERY_PRODUIT_ID"
private const val EXTRA_QUERY_PRODUIT_COLORI_ID = "com.remilapointe.laser.QUERY_PRODUIT_COLORI_ID"
private const val EXTRA_QUERY_PRODUIT_TAILLE_ID = "com.remilapointe.laser.QUERY_PRODUIT_TAILLE_ID"
private const val EXTRA_QUERY_PRODUIT_PLACELOGO_ID = "com.remilapointe.laser.QUERY_PRODUIT_PLACELOGO_ID"
private const val EXTRA_QUERY_COLORI_LIST = "com.remilapointe.laser.QUERY_COLORI_LIST"
private const val EXTRA_QUERY_TAILLE_LIST = "com.remilapointe.laser.QUERY_TAILLE_LIST"
private const val EXTRA_QUERY_PLACELOGO_LIST = "com.remilapointe.laser.QUERY_PLACELOGO_LIST"

class ProduitAddActivity : AppCompatActivity(), AdapterView.OnItemSelectedListener {

    private lateinit var tvProduitId: TextView
    private lateinit var spProduitAddColori: Spinner
    private lateinit var spProduitAddTaille: Spinner
    private lateinit var spProduitAddCPlacelogo: Spinner
//    private lateinit var coloriViewModel: ColoriViewModel
//    private lateinit var tailleViewModel: TailleViewModel
//    private lateinit var placelogoViewModel: PlaceLogoViewModel
    private lateinit var produitViewModel: ProduitViewModel
    private var produitColoriId: Int = 0
    private var produitTailleId: Int = 0
    private var produitPlaceLogoId: Int = 0
    private var produitColori: String? = ""
    private var produitTaille: String? = ""
    private var produitPlacelogo: String? = ""
    //var list_of_items = arrayOf("Item 1", "Item 2", "Item 3")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        d("onCreate begin")
        setContentView(R.layout.produit_add_activity)

        tvProduitId = findViewById(R.id.tvProduitAddId)
//        val productId = intent.getIntExtra(EXTRA_QUERY_PRODUIT_ID, 0)

        produitViewModel = ViewModelProvider(this).get(ProduitViewModel::class.java)
        produitColoriId = intent.getIntExtra(EXTRA_QUERY_PRODUIT_COLORI_ID, 0)
        produitColori = produitViewModel.getColoriById(produitColoriId)
        val coloriList = intent.getStringArrayExtra(EXTRA_QUERY_COLORI_LIST)!!

        spProduitAddColori = findViewById(R.id.spProduitAddColori)
        val adaptColori = ArrayAdapter(this, android.R.layout.simple_spinner_item, coloriList)
        adaptColori.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spProduitAddColori.adapter = adaptColori
        spProduitAddColori.onItemSelectedListener = this

        produitTailleId = intent.getIntExtra(EXTRA_QUERY_PRODUIT_TAILLE_ID, 0)
        produitTaille = produitViewModel.getTailleById(produitTailleId)
        val tailleList = intent.getStringArrayExtra(EXTRA_QUERY_TAILLE_LIST)!!

        spProduitAddTaille = findViewById(R.id.spProduitAddTaille)
        val adaptTaille = ArrayAdapter(this, android.R.layout.simple_spinner_item, tailleList)
        adaptTaille.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spProduitAddTaille.adapter = adaptTaille
        spProduitAddTaille.onItemSelectedListener = this

        produitPlaceLogoId = intent.getIntExtra(EXTRA_QUERY_PRODUIT_PLACELOGO_ID, 0)
        produitPlacelogo = produitViewModel.getPlaceLogoById(produitPlaceLogoId)
        val placelogoList = intent.getStringArrayExtra(EXTRA_QUERY_PLACELOGO_LIST)!!

        spProduitAddCPlacelogo = findViewById(R.id.spProduitAddPlacelogo)
        val adaptPlacelogo = ArrayAdapter(this, android.R.layout.simple_spinner_item, placelogoList)
        adaptPlacelogo.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spProduitAddCPlacelogo.adapter = adaptPlacelogo
        spProduitAddCPlacelogo.onItemSelectedListener = this

        val button = findViewById<Button>(R.id.bt_save_produit)
        button.setOnClickListener {
            d("click on produit save button")
            val replyIntent = Intent()
            if (TextUtils.isEmpty(tvProduitAddId.text)) {
                setResult(Activity.RESULT_CANCELED, replyIntent)
            } else {
                val elem = tvProduitId.text.toString()
                d("produitId to save: $elem")
                replyIntent.putExtra(EXTRA_REPLY_PRODUIT_ID, elem)
                produitColoriId = produitViewModel.getColoriId(coloriList[spProduitAddColori.selectedItemPosition])
                replyIntent.putExtra(EXTRA_REPLY_PRODUIT_COLORI_ID, produitColoriId)
                produitTailleId = produitViewModel.getTailleId(tailleList[spProduitAddTaille.selectedItemPosition])
                replyIntent.putExtra(EXTRA_REPLY_PRODUIT_TAILLE_ID, produitTailleId)
                produitPlaceLogoId = produitViewModel.getPlaceLogoId(placelogoList[spProduitAddCPlacelogo.selectedItemPosition])
                replyIntent.putExtra(EXTRA_REPLY_PRODUIT_PLACELOG_ID, produitPlaceLogoId)
                setResult(Activity.RESULT_OK, replyIntent)
            }
        }
    }

    override fun onItemSelected(p0: AdapterView<*>?, view: View?, position: Int, id: Long) {
        //var sel = list_of_items[position]
    }

    override fun onNothingSelected(p0: AdapterView<*>?) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    companion object {
        const val EXTRA_REPLY_PRODUIT_ID = "com.remilapointe.laser.REPLY_PRODUIT_ID"
        const val EXTRA_REPLY_PRODUIT_COLORI_ID = "com.remilapointe.laser.REPLY_PRODUIT_COLORI_ID"
        const val EXTRA_REPLY_PRODUIT_TAILLE_ID = "com.remilapointe.laser.REPLY_PRODUIT_TAILLE_ID"
        const val EXTRA_REPLY_PRODUIT_PLACELOG_ID = "com.remilapointe.laser.REPLY_PRODUIT_PLACELOGO_ID"
    }

}
