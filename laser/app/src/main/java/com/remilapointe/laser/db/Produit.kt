package com.remilapointe.laser.db

import androidx.annotation.NonNull
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = Produit.TABLE_NAME)
data class Produit (
    @PrimaryKey(autoGenerate = true)
    @NonNull override val id: Int,
    @ColumnInfo(index = true)
    @NonNull override val elem: String
) : IdValue {

    override fun getString(): String {
        return elem
    }

    companion object {
        const val ELEM = "produit"
        const val TABLE_NAME = "laser_$ELEM"
        const val PRIM_KEY = "id"
        const val SORT_FIELD = "elem"
    }
}
