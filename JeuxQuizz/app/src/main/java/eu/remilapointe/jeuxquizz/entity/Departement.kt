package eu.remilapointe.jeuxquizz.entity

import androidx.annotation.NonNull
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(
    tableName = Departement.TABLE_NAME
)
data class Departement(
    @PrimaryKey(autoGenerate = true)
    @NonNull var id: Long,

    var name: String = "",
    var numero: String,
    var regionId: Long,
    var cheflieuId: Long,
    var pronom: String

) {
    companion object {
        const val ELEM = "departement"
        const val TABLE_NAME = "Jeux_Quizz_departement"
        const val PRIM_KEY = "id"
        const val DEPT_NAME = "name"
        const val DEPT_NUMERO = "numero"
        const val DEPT_REGIONID = "regionId"
        const val DEPT_CHEFLIEUID = "cheflieuId"
        const val DEPT_PRONOM = "pronom"
    }
}