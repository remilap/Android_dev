package eu.remilapointe.country

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import eu.remilapointe.country.databinding.ItemListContentBinding
import eu.remilapointe.country.entity.Country
import mu.KotlinLogging

private lateinit var binding: ItemListContentBinding
private val logger = KotlinLogging.logger {}

class SimpleItemRecyclerViewAdapter(
    private val parentActivity: ItemListActivity,
    private val values: MutableList<Country>
) :
    RecyclerView.Adapter<SimpleItemRecyclerViewAdapter.ViewHolder>() {

    private val onClickListener: View.OnClickListener

    init {
        onClickListener = View.OnClickListener { v ->
            val item = v.tag as Country
            logger.debug("launch one Pane with ItemDetailActivity")
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
        binding = ItemListContentBinding.inflate(LayoutInflater.from(parent.context))
//        val view = binding.root
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
        val idView: TextView = view.findViewById(R.id.id_text)
        val abbrevView: TextView = view.findViewById(R.id.tvAbbrev)
        val languageView: TextView = view.findViewById(R.id.tvLanguage)
        val ueEntryView: TextView = view.findViewById(R.id.tvUeEntry)
        val population: TextView = view.findViewById(R.id.tvPopulation)
    }
}