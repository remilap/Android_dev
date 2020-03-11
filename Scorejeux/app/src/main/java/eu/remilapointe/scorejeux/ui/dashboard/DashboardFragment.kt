package eu.remilapointe.scorejeux.ui.dashboard

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ListView
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.log4k.d
import eu.remilapointe.scorejeux.R
import eu.remilapointe.scorejeux.adapter.JoueurListAdapter
import eu.remilapointe.scorejeux.entity.Joueur

class DashboardFragment(passedContext: Context) : Fragment() {

    val passThroughContext: Context = passedContext

    private lateinit var dashboardViewModel: DashboardViewModel
    private lateinit var adapter: JoueurListAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        dashboardViewModel = ViewModelProvider(this).get(DashboardViewModel::class.java)
        adapter = JoueurListAdapter(passThroughContext, dashboardViewModel) { item: Joueur -> itemItemClicked(item) }
    }

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        val root = inflater.inflate(R.layout.fragment_dashboard, container, false)
        val textView: TextView = root.findViewById(R.id.text_dashboard)
        dashboardViewModel.text.observe(viewLifecycleOwner, Observer {
            textView.text = it
        })
        val joueurLv: ListView = root.findViewById(R.id.lv_joueurs)
        joueurLv.adapter = adapter
        dashboardViewModel.allJoueurs.observe(viewLifecycleOwner, Observer {
            adapter
        })
        return root
    }

    private fun itemItemClicked(item: Joueur): View.OnClickListener {
        //val intent = Intent(this@ColoriFragment.context, ColoriAddActivity::class.java)
        //intent.putExtra(ColoriAddActivity.EXTRA_QUERY_COLORI, item.elem)
        d("click on the ${Joueur.ELEM} ${Joueur.PRIM_KEY}: ${item.id}, launch update")
        //getElements()
//        startActivityForResult(
//            activity!!.ArticleDetailIntent(item, produitsList, colorisList, taillesList, placelogosList),
//            updateArticleActivityRequestCode
//        )
        return View.OnClickListener {  }
    }

}
