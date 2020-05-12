package eu.remilapointe.jeuxquizz.entity

import androidx.annotation.NonNull
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(
    tableName = Animal.TABLE_NAME
)
data class Animal(
    @PrimaryKey(autoGenerate = true)
    @NonNull var id: Long,

    var name: String = ""

) {
    companion object {
        const val ELEM = "animal"
        const val TABLE_NAME = "Jeux_Quizz_animal"
        const val PRIM_KEY = "id"
        const val ABBREV = "name"
    }
}