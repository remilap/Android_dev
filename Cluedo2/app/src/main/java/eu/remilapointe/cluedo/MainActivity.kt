package eu.remilapointe.cluedo

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.EditText
import androidx.appcompat.app.AlertDialog
import com.log4k.DefaultAppender
import com.log4k.Level
import com.log4k.Log4k
import com.log4k.android.AndroidAppender
import eu.remilapointe.cluedo.entity.JeuActif
import eu.remilapointe.cluedo.entity.JeuStandard
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.ask_nom_joueur.*
import org.jetbrains.anko.toast
import java.io.File
import java.io.PrintWriter
import kotlin.text.StringBuilder

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        if (BuildConfig.DEBUG) {
            Log4k.add(Level.Verbose, ".*", AndroidAppender())
            Log4k.add(Level.Verbose, "com\\.log4k\\.sample\\..+", DefaultAppender())
            Log4k.add(Level.Verbose, ".*", DefaultAppender(writer = PrintWriter(File(externalCacheDir, "debug-log.txt"))))
        } else {
            Log4k.add(Level.Assert, "com\\.log4k\\.sample\\..+", DefaultAppender(writer = PrintWriter(File(filesDir, "log.txt"))))
        }

        val jeu = JeuStandard(resources)

        bt_distrib_cartes.setOnClickListener {
            val nbJoueurs = if (rb_ndj_3.isChecked) 3 else 4
            toast("$nbJoueurs joueurs en jeu")
            for (i in 1..nbJoueurs) {
                toast("ask for name of player $i")
                dialogAskNomJoueur(it, jeu)
            }
            jeu.distribution()
            var sb = StringBuilder(getString(R.string.st_enigme))
            sb.append(" : ")
            sb.append(System.lineSeparator())
            jeu.listEnigme.forEach {
                sb.append(it.nom)
                sb.append(System.lineSeparator())
            }
            tv_enigme.text = sb
            jeu.listJoueurs.forEachIndexed { j, joueur ->
                sb = StringBuilder(getString(R.string.st_joueur_num))
                sb.append("  ").append(j-+1).append(": ")
                sb.append(joueur.nom)
                sb.append(System.lineSeparator())
                joueur.cartes.forEach { sb.append(it.nom).append(System.lineSeparator()) }
                when (j) {
                    0 -> tv_joueur1.text = sb
                    1 -> tv_joueur2.text = sb
                    2 -> tv_joueur3.text = sb
                    3 -> tv_joueur4.text = sb
                }
            }
        }
    }

    private fun dialogAskNomJoueur(view: View, jeuTmp: JeuActif) {
        val builder = AlertDialog.Builder(this)
        val inflater = layoutInflater
        builder.setTitle("With EditText")
        val dialogLayout = inflater.inflate(R.layout.ask_nom_joueur, null)
        //val et_nom_joueur  = dialogLayout.findViewById<EditText>(R.id.et_nom_joueur)
        builder.setView(dialogLayout)
        builder.setPositiveButton("OK") { dialogInterface, i ->
            val nomJoueur = et_nom_joueur.text.toString()
            toast("Le nom du joueur est $nomJoueur")
            jeuTmp.addJoueur(nomJoueur)
        }
        builder.show()
    }

}
