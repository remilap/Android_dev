package eu.remilapointe.country

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.log4k.d
import eu.remilapointe.country.entity.Country
import kotlinx.android.synthetic.main.item_list_content.view.*

class SimpleItemRecyclerViewAdapter(
    private val parentActivity: ItemListActivity,
    private val values: MutableList<Country>
) :
    RecyclerView.Adapter<SimpleItemRecyclerViewAdapter.ViewHolder>() {

    private val onClickListener: View.OnClickListener

    init {
        onClickListener = View.OnClickListener { v ->
            val item = v.tag as Country
            d("launch one Pane with ItemDetailActivity")
            val intent = Intent(
                v.context,
                ItemDetailActivity::class.java
            ).apply {
                putExtra(ItemDetailActivity.ARG_ITEM_ID, item.id)
            }
            v.context.startActivity(intent)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_list_content, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = values[position]
        holder.idView.text = item.id.toString()
        holder.abbrevView.text = item.abbrev
        holder.languageView.text = item.languages
        holder.ueEntryView.text = item.UEEntryIn.toString()
        holder.population.text = item.UEExitIn.toString()

        with(holder.itemView) {
            tag = item
            setOnClickListener(onClickListener)
        }
    }

    override fun getItemCount() = values.size

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val idView: TextView = view.id_text
        val abbrevView: TextView = view.tvAbbrev
        val languageView: TextView = view.tvLanguage
        val ueEntryView: TextView = view.tvUeEntry
        val population: TextView = view.tvPopulation
    }
}