package eu.remilapointe.cluedo.entity

import android.content.res.Resources
import com.log4k.d
import eu.remilapointe.cluedo.R

class JeuStandard(res: Resources) : JeuActif(res) {

    init {
        val pers_name = res.getStringArray(R.array.pers_name)
        val pers_coul = res.getStringArray(R.array.pers_coul)
        val pers_posx = res.getIntArray(R.array.pers_posx)
        val pers_poxy = res.getIntArray(R.array.pers_posy)
        for (i in pers_name.indices) {
            val pers = Personnage(pers_name[i], pers_coul[i], pers_posx[i], pers_poxy[i])
            addPers(pers)
        }
        val arme_name = res.getStringArray(R.array.arme_name)
        for (i in arme_name.indices) {
            val arme = Arme(arme_name[i])
            addArme(arme)
        }
        val lieu_name = res.getStringArray(R.array.lieu_name)
        for (i in lieu_name.indices) {
            val lieu = Lieu(lieu_name[i])
            addLieu(lieu)
        }
    }

}
