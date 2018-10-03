package com.remilapointe.cluedo.activities

import android.content.Context
import android.content.res.Resources
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import com.remilapointe.cluedo.R
import com.remilapointe.cluedo.Util
import com.remilapointe.cluedo.game.cards.Card
import com.remilapointe.cluedo.game.cards.CardPack
import kotlinx.android.synthetic.main.card_entry.view.*

class CardImageAdapter : BaseAdapter {
    var cardsList = ArrayList<Card>()
    var context: Context? = null

    constructor(context: Context, cardsList: ArrayList<Card>) : super() {
        this.context = context
        this.cardsList = cardsList
    }

    override fun getCount(): Int {
        return cardsList.size
    }

    override fun getItem(position: Int): Any {
        return cardsList[position]
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val card = this.cardsList[position]

        var inflator = context!!.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        var cardView = inflator.inflate(R.layout.card_entry, null)
        var imgName = "cluedo_"
        if (cardsList[position].idx < CardPack.nbPlaces) {
            imgName += "rooms2_${cardsList[position].idx + 1}"
        } else if (cardsList[position].idx < CardPack.nbPlaces + CardPack.nbPersons) {
            imgName += "people2_${cardsList[position].idx - CardPack.nbPlaces + 1}"
        } else {
            imgName += "arm2_${cardsList[position].idx - CardPack.nbPlaces - CardPack.nbPersons + 1}"
        }
        imgName += "_en"
        Util.log("position: $position, imgName: $imgName")
        var id = Resources.getSystem().getIdentifier(imgName, "drawable", context!!.packageName)
        Util.log("img id: $id")
        cardView.iv_one_card.setImageResource(id)
        cardView.tv_card_name.text = cardsList[position].name

        return cardView
    }

}
