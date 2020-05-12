package eu.remilapointe.jeuxquizz.entity

import androidx.annotation.NonNull
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(
    tableName = Region.TABLE_NAME
)
data class Region(
    @PrimaryKey(autoGenerate = true)
    @NonNull var id: Long,

    var num: Int = 0,
    var name: String = ""

) {
    companion object {
        const val ELEM = "region"
        const val TABLE_NAME = "Jeux_Quizz_region"
        const val PRIM_KEY = "id"
        const val REGION_NUM = "num"
        const val REGION_NAME = "name"
    }
}