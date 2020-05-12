package eu.remilapointe.jeuxquizz.repository

import androidx.annotation.WorkerThread
import androidx.lifecycle.LiveData
import com.log4k.d
import eu.remilapointe.jeuxquizz.dao.DepartementDao
import eu.remilapointe.jeuxquizz.dao.RegionDao
import eu.remilapointe.jeuxquizz.dao.VilleDao
import eu.remilapointe.jeuxquizz.entity.Departement
import eu.remilapointe.jeuxquizz.entity.Region
import eu.remilapointe.jeuxquizz.entity.Ville
import kotlin.text.StringBuilder

class QuizzRegionRepository(private val regionDao: RegionDao, private val departementDao: DepartementDao, private val villeDao: VilleDao) {

    val allRegions: LiveData<MutableList<Region>> = regionDao.getAll()
    val allDepartements: LiveData<MutableList<Departement>> = departementDao.getAll()
    val allVilles: LiveData<MutableList<Ville>> = villeDao.getAll()

    @WorkerThread
    suspend fun insertRegion(num: Int, name: String) : Long {
        d("Repo insert region num=$num, name=$name")
        var region = Region(0, num, name)
        regionDao.insert(region)
        region = regionDao.getByNum(num)
        d("inserted region id=${region.id}, num=${region.num}, name=${region.name}")
        return region.id
    }

    @WorkerThread
    suspend fun removeRegionByNum(num: Int) : Int {
        d("Repo removeRegionByNum num=$num")
        val region = regionDao.getByNum(num)
        val res = regionDao.remove(region.id)
        d("removed region id=${region.id}, res=$res")
        return res
    }

    @WorkerThread
    suspend fun removeRegion(region: Region) : Int {
        d("Repo removeRegion id=${region.id}, num=${region.num}, name=${region.name}")
        val res = regionDao.remove(region.id)
        d("removed region id=${region.id}, res=$res")
        return res
    }

    @WorkerThread
    fun getRegion(key: Long) : Region {
        d("Repo get region id=$key")
        val region = regionDao.get(key)
        d("gotten region id=${region.id}, num=${region.num}, name=${region.name}")
        return region
    }

    @WorkerThread
    fun getRegionByNum(num: Int) : Region {
        d("Repo get region num=$num")
        val region = regionDao.getByNum(num)
        d("gotten region id=${region.id}, num=${region.num}, name=${region.name}")
        return region
    }

    @WorkerThread
    fun getRegionByName(name: String) : Region {
        d("Repo get region name=$name")
        val region = regionDao.getByName(name)
        d("gotten region id=${region.id}, num=${region.num}, name=${region.name}")
        return region
    }

    fun regionToString(region: Region) : String {
        val sb = StringBuilder("Region[id=")
        sb.append(region.id)
        sb.append(", num=")
        sb.append(region.num)
        sb.append(", name=")
        sb.append(region.name)
        sb.append("]")
        return sb.toString()
    }

    @WorkerThread
    suspend fun insertDepartement(name: String, numero: String, regionId: Long, chefLieuId: Long, pronom: String) : Long {
        d("Repo insert departement numero=$numero, name=$name, regionId=$regionId, chefLieuId=$chefLieuId")
        var dept = Departement(0, name, numero, regionId, chefLieuId, pronom)
        departementDao.insert(dept)
        dept = departementDao.getByNum(numero)
        d("inserted departement id=${dept.id}, numero=${dept.numero}, name=${dept.name}, regionId=${dept.regionId}, chefLieuId=${dept.cheflieuId}")
        return dept.id
    }

    @WorkerThread
    suspend fun removeDepartementByNum(numero: String) : Int {
        d("Repo removeDepartementByNum numero=$numero")
        val dept = departementDao.getByNum(numero)
        val res = departementDao.remove(dept.id)
        d("removed departement id=${dept.id}, res=$res")
        return res
    }

    @WorkerThread
    suspend fun removeDepartement(dept: Departement) : Int {
        d("Repo removeDepartement id=${dept.id}, numero=${dept.numero}, name=${dept.name}, regionId=${dept.regionId}, chefLieuId=${dept.cheflieuId}")
        val res = departementDao.remove(dept.id)
        d("removed departement id=${dept.id}, res=$res")
        return res
    }

    @WorkerThread
    fun getDepartement(key: Long) : Departement {
        d("Repo get departement id=$key")
        val dept = departementDao.get(key)
        d("gotten departement id=${dept.id}, numero=${dept.numero}, name=${dept.name}")
        return dept
    }

    @WorkerThread
    fun getDepartementByNum(numero: String) : Departement {
        d("Repo get departement num=$numero")
        val dept = departementDao.getByNum(numero)
        d("gotten departement id=${dept.id}, numero=${dept.numero}, name=${dept.name}")
        return dept
    }

    fun departementToString(dept: Departement) : String {
        val sb = StringBuilder("Departement[id=")
        sb.append(dept.id)
        sb.append(", numero=")
        sb.append(dept.numero)
        sb.append(", name=")
        sb.append(dept.name)
        sb.append(", region=")
        val region = getRegion(dept.regionId)
        sb.append(region.name)
        sb.append(", chef-lieu=")
        val ville = getVille(dept.cheflieuId)
        sb.append(ville.name)
        sb.append("]")
        return sb.toString()
    }

    @WorkerThread
    suspend fun insertVille(name: String, deptId: Long) : Long {
        d("Repo insert ville name=$name, deptId=$deptId")
        var ville = Ville(0, name, deptId)
        villeDao.insert(ville)
        ville = villeDao.get(name)
        d("inserted ville id=${ville.id}, name=${ville.name}, deptId=${ville.deptId}")
        return ville.id
    }

    @WorkerThread
    suspend fun removeVille(ville: Ville) : Int {
        d("Repo removeVille id=${ville.id}, name=${ville.name}, deptId=${ville.deptId}")
        val res = villeDao.remove(ville.id)
        d("removed ville id=${ville.id}, res=$res")
        return res
    }

    @WorkerThread
    fun getVille(key: Long) : Ville {
        d("Repo get ville id=$key")
        val ville = villeDao.get(key)
        d("gotten ville id=${ville.id}, name=${ville.name}")
        return ville
    }

    fun villeToString(ville: Ville) : String {
        val sb = StringBuilder("Ville[id=")
        sb.append(ville.id)
        sb.append(", name=")
        sb.append(ville.name)
        sb.append(", departement=")
        val dept = getDepartement(ville.deptId)
        sb.append(dept.name)
        sb.append("]")
        return sb.toString()
    }

}
