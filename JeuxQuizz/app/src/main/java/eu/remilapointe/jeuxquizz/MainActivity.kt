package eu.remilapointe.jeuxquizz

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.log4k.d
import eu.remilapointe.jeuxquizz.activity.Quizz01StartActivity
import eu.remilapointe.jeuxquizz.db.QuizzDb
import eu.remilapointe.jeuxquizz.entity.Departement
import eu.remilapointe.jeuxquizz.entity.Region
import eu.remilapointe.jeuxquizz.entity.Ville
import eu.remilapointe.jeuxquizz.repository.QuizzRegionRepository
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        bt_quizz_test.setOnClickListener {
            val intent = Intent(applicationContext, Quizz01StartActivity::class.java)
            intent.putExtra(QUIZ_REFERENCE, "test")
            startActivity(intent)
        }

        bt_quizz_premiere_lettre.setOnClickListener {
            val intent = Intent(applicationContext, Quizz01StartActivity::class.java)
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
            Log.d("MAIN","Region line: $regionLine")
            val elems = regionLine.split(":")
            region = Region(0, elems[0].toInt(), elems[1])
            regionDao.insert(region)
            Log.d("MAIN","Region inserted: ${repository.regionToString(region)}")
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

    companion object {
        const val QUIZ_REFERENCE = "QUIZ_REFERENCE"
    }
}
