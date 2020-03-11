package eu.remilapointe.scorejeux.entity

import androidx.annotation.NonNull
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(
    tableName = Joueur.TABLE_NAME
)
data class Joueur(
    @PrimaryKey(autoGenerate = true)
    @NonNull var id: Long,

    var name: String = "inconnu"

) {
    companion object {
        const val ELEM = "joueur"
        const val TABLE_NAME = "${Database.DB_NAME}_${ELEM}"
        const val PRIM_KEY = "id"
        const val NAME = "name"
    }
}