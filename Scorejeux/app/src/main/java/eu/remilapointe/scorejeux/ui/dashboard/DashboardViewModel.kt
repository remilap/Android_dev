package eu.remilapointe.scorejeux.ui.dashboard

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.log4k.d
import eu.remilapointe.scorejeux.db.JeuDatabase
import eu.remilapointe.scorejeux.entity.Joueur
import eu.remilapointe.scorejeux.repo.JoueurRepo

class DashboardViewModel(application: Application) : AndroidViewModel(application) {

    private val joueurRepo: JoueurRepo

    val allJoueurs: LiveData<MutableList<Joueur>>

    private val _text = MutableLiveData<String>().apply {
        value = "This is dashboard Fragment"
    }
    val text: LiveData<String> = _text

    private val CL = this::class.java.simpleName

    init {
        d("$CL:init")

        val joueurDao = JeuDatabase.getDatabase(application).JoueurDao()
        joueurRepo = JoueurRepo(joueurDao)
        allJoueurs = joueurDao.getAll()
        if (allJoueurs.value == null) {
            d("$CL:all${Joueur.ELEM}, size: 0")
        } else {
            d("$CL:all${Joueur.ELEM}, size: " + allJoueurs.value?.size)
        }
    }

}
