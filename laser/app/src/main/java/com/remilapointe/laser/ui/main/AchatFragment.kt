package com.remilapointe.laser.ui.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.DatePicker
import android.widget.Spinner
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.log4k.d
import com.remilapointe.laser.R
import com.remilapointe.laser.db.Article
import com.remilapointe.laser.db.TransactionArticles
import com.remilapointe.laser.ui.viewmodel.LaserViewModel
import kotlinx.android.synthetic.main.achat_fragment.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.jetbrains.anko.longToast
import java.time.LocalDate

class AchatFragment : Fragment(), DatePicker.OnDateChangedListener {

    companion object {
        fun newInstance() : AchatFragment {
            return AchatFragment()
        }
    }

    private lateinit var laserViewModel: LaserViewModel
    private var articlesList = mutableListOf<Article>()
    private var artStringList = arrayListOf<String>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val root = inflater.inflate(R.layout.achat_fragment, container, false)
        val ctx = activity?.applicationContext

        laserViewModel = ViewModelProvider(this).get(LaserViewModel::class.java).apply {  }

        val adaptArticles = ArrayAdapter(ctx!!, android.R.layout.simple_spinner_dropdown_item, artStringList)
        val spAchatArticle = root.findViewById<Spinner>(R.id.spAchatArticle)
        spAchatArticle.adapter = adaptArticles
        spAchatArticle.setSelection(0)

        laserViewModel.allArticles.observe(this, Observer { it?.let { list ->
            articlesList = list
            val sb = StringBuffer()
            articlesList.forEach {article ->
                val s = laserViewModel.getArticleStringByArticle(article)
                artStringList.add(s)
                sb.append(s).append(", ")
            }
            d("list of ${Article.ELEM}s: $sb (${artStringList.size} elems)")
            adaptArticles.clear()
            adaptArticles.addAll(artStringList)
            adaptArticles.setNotifyOnChange(true)
            adaptArticles.notifyDataSetChanged()
        } })

        val today = LocalDate.now()
        val dpAchatDate = root.findViewById<DatePicker>(R.id.dpAchatDate)
        dpAchatDate.init(today.year, today.monthValue, today.dayOfMonth, this)

        val bt_achat_cancel = root.findViewById<Button>(R.id.bt_achat_cancel)
        bt_achat_cancel.setOnClickListener {
            activity?.finish()
        }

        val bt_achat_ok = root.findViewById<Button>(R.id.bt_achat_ok)
        bt_achat_ok.setOnClickListener {
            val refArt = artStringList[spAchatArticle.selectedItemPosition]
            val nbArt = edAchatNb.text.toString().toIntOrNull()
            val prixAchat = edAchatPrixHT.toString().toDoubleOrNull()
            val tva = edAchatTVA.toString().toDoubleOrNull()
            val remise = edAchatRemise.toString().toDoubleOrNull()
            val datAchat = LocalDate.of(dpAchatDate.year, dpAchatDate.month, dpAchatDate.dayOfMonth)
            if (nbArt != null && prixAchat != null && tva != null && remise != null) {
                val refArts = refArt.split("/")
                if (refArts.size == 4) {
                    val viewmodelCoroutineScope = CoroutineScope(Dispatchers.IO)
                    viewmodelCoroutineScope.launch {
                        val produitId = laserViewModel.getProduitId(refArts[0])
                        val coloriId = laserViewModel.getColoriId(refArts[1])
                        val tailleId = laserViewModel.getTailleId(refArts[2])
                        val placeLogoId = laserViewModel.getPlaceLogoId(refArts[3])
                        val article = laserViewModel.getArticleByCompIds(produitId, coloriId, tailleId, placeLogoId)
                        if (article != null) {
                            laserViewModel.insertTransactionArticle(article.id, TransactionArticles.TRANSACTION_TYPE_ACHAT, nbArt, prixAchat, tva, remise, datAchat)
                            laserViewModel.insertArticlesEnStock(article.id, nbArt, nbArt, datAchat, prixAchat - remise)
                        } else {
                            ctx.longToast(getString(R.string.unable_to_find_article, refArt))
                        }
                    }
                } else {
                    ctx.longToast(getString(R.string.unable_to_find_article, refArt))
                }
            } else {
                ctx.longToast(R.string.all_fields_must_be_filled)
            }
        }

        return root
    }

    override fun onDateChanged(view: DatePicker?, year: Int, monthOfYear: Int, dayOfMonth: Int) {
        //TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

}
