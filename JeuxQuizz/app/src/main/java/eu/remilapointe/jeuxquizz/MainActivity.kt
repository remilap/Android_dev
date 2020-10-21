package eu.remilapointe.jeuxquizz

import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.datastore.DataStore
import androidx.datastore.preferences.*
import androidx.lifecycle.asLiveData
import androidx.lifecycle.lifecycleScope
import com.log4k.d
import eu.remilapointe.jeuxquizz.SettingsManager.Companion.QUIZ_REFERENCE
import eu.remilapointe.jeuxquizz.SettingsManager.Companion.SELECTED_QUIZ
import eu.remilapointe.jeuxquizz.SettingsManager.Companion.quizList
import eu.remilapointe.jeuxquizz.activity.Quiz01StartActivity
import eu.remilapointe.jeuxquizz.activity.QuizDepartementStartActivity
import eu.remilapointe.jeuxquizz.db.QuizzDb
import eu.remilapointe.jeuxquizz.entity.Departement
import eu.remilapointe.jeuxquizz.entity.Region
import eu.remilapointe.jeuxquizz.entity.Ville
import eu.remilapointe.jeuxquizz.repository.QuizzRegionRepository
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import org.jetbrains.anko.toast


class MainActivity : AppCompatActivity() {

    private lateinit var settingsManager: SettingsManager
    private var selectedQuiz: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        settingsManager = SettingsManager(applicationContext)

        bt_quiz_test.setOnClickListener {
            var selectedItem: Int = 0
            val builder = AlertDialog.Builder(this)
            builder.setTitle("Choose some animals")

// Add a checkbox list
            builder.setSingleChoiceItems(quizList, 0,
                DialogInterface.OnClickListener { _, which ->
                    selectedItem = which
                    lifecycleScope.launch { settingsManager.setSelectedQuiz(which) }
                }
            )
// Add OK and Cancel buttons
            builder.setPositiveButton("OK", DialogInterface.OnClickListener { dialog, which ->
                // The user clicked OK
                toast("choix : ${quizList[selectedItem]}")
                val intent = Intent(applicationContext, Quiz01StartActivity::class.java)
                intent.putExtra(QUIZ_REFERENCE, selectedItem)
                startActivity(intent)
            })
            builder.setNegativeButton("Cancel", DialogInterface.OnClickListener{ dialog, which ->
                // The user clicked Cancel
                dialog.cancel()
            })
// Create and show the alert dialog
            val dialog: AlertDialog = builder.create()
            dialog.show()
        }

        bt_quiz_departement.setOnClickListener {
            val intent = Intent(applicationContext, QuizDepartementStartActivity::class.java)
            intent.putExtra(QUIZ_REFERENCE, "test")
            startActivity(intent)
        }

        GlobalScope.launch(Dispatchers.IO) {
                val db = QuizzDb.getDatabase(applicationContext)
                loadRegionResources(applicationContext, db)

            }
        }

    private suspend fun loadRegionResources(ctx: Context, db: QuizzDb) {
        val regionDao = db.regionDao()
        var region = regionDao.getByNum(1)
        if (region.name == "Auvergne-Rhône-Alpes") return
        val departementDao = db.departementDao()
        val villeDao = db.villeDao()
        val repository = QuizzRegionRepository(regionDao, departementDao, villeDao)

        val regions = ctx.resources.getStringArray(R.array.region)
        // with following format: <item>1:Auvergne-Rhône-Alpes</item>
        for (regionLine in regions) {
            Log.d("MAIN", "Region line: $regionLine")
            val elems = regionLine.split(":")
            region = Region(0, elems[0].toInt(), elems[1])
            regionDao.insert(region)
            Log.d("MAIN", "Region inserted: ${repository.regionToString(region)}")
        }

        val departements = ctx.resources.getStringArray(R.array.departement)
        // with following format: <item>05:Hautes-Alpes:3:Gap:des </item>
        for (deptLine in departements) {
            val elems = deptLine.split(":")
            d("Dept line: $deptLine, nb elems: ${elems.size}")
            region = regionDao.getByNum(elems[2].toInt())
            val regionId = region.id
            var dept = Departement(0, elems[1], elems[0], regionId, 0, elems[4])
            departementDao.insert(dept)
            dept = departementDao.getByNum(elems[0])
            d("Dept inserted: ${repository.departementToString(dept)}")
            val ville = Ville(0, elems[3], dept.id)
            villeDao.insert(ville)
            d("Ville inserted: ${repository.villeToString(ville)}")
            departementDao.update(dept.id, dept.name, dept.numero, regionId, ville.id, dept.pronom)
            d("Dept updated: ${repository.departementToString(dept)}")
        }
    }

}
