package eu.remilapointe.scorejeux.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.log4k.d
import eu.remilapointe.scorejeux.R
import eu.remilapointe.scorejeux.entity.Joueur
import eu.remilapointe.scorejeux.ui.dashboard.DashboardViewModel

class JoueurListAdapter internal constructor(
    context: Context,
    private val dashboardViewModel: DashboardViewModel,
    private val clickListener: (Joueur) -> View.OnClickListener
) : RecyclerView.Adapter<JoueurListAdapter.JoueurViewHolder>() {

    private val inflater: LayoutInflater = LayoutInflater.from(context)
    private var joueursList = mutableListOf<Joueur>()

    inner class JoueurViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val idItemView: TextView = itemView.findViewById(R.id.tv_id_joueur)
        val nomItemView: TextView = itemView.findViewById(R.id.tv_nom_joueur)
        fun bind(joueur: Joueur, listener: (Joueur) -> View.OnClickListener) = idItemView.setOnClickListener( listener(joueur) )
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): JoueurViewHolder {
        val itemView = inflater.inflate(R.layout.joueur_recyclerview_item, parent, false)
        return JoueurViewHolder((itemView))
    }

    override fun onBindViewHolder(holder: JoueurViewHolder, position: Int) {
        val current = joueursList[position]
        d("${Joueur.ELEM} ${Joueur.PRIM_KEY}: ${current.id}, ${current.name}")
        holder.idItemView.text = current.id.toString()
        holder.nomItemView.text = current.name
        holder.bind(current, clickListener)
    }

    internal fun setJoueurs(strs: MutableList<Joueur>) {
        this.joueursList = strs
        d("set${Joueur.ELEM} with ${strs.size} elems")
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int = joueursList.size

}
