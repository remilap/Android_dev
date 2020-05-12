package eu.remilapointe.jeuxquizz.entity

import androidx.annotation.NonNull
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(
    tableName = Ville.TABLE_NAME
)
data class Ville(
    @PrimaryKey(autoGenerate = true)
    @NonNull var id: Long,

    var name: String = "",
    var deptId: Long

) {
    companion object {
        const val ELEM = "ville"
        const val TABLE_NAME = "Jeux_Quizz_ville"
        const val PRIM_KEY = "id"
        const val VILLE_NAME = "name"
        const val VILLE_DEPTID = "deptId"
    }
}