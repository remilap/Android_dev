package eu.remilapointe.scorejeux.entity

import androidx.annotation.NonNull
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(
    tableName = Jeu.TABLE_NAME
)
data class Jeu(
    @PrimaryKey(autoGenerate = true)
    @NonNull var id: Long,

    var name: String,
    var nbManches: Int = 0,
    var ordreFixe: Boolean = false

) {
    companion object {
        const val ELEM = "jeu"
        const val TABLE_NAME = "${Database.DB_NAME}_${ELEM}"
        const val PRIM_KEY = "id"
        const val NAME = "name"
        const val NBMANCHES = "nbManches"
        const val ORDREFIXE = "ordreFixe"
    }
}
