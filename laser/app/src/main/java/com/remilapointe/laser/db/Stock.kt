package com.remilapointe.laser.db

import androidx.annotation.NonNull
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Stock(
    @PrimaryKey(autoGenerate = true)
    @NonNull val id: Int,
    @ColumnInfo(index = true)
    val titreStock: String,
    val articlesEnStockList: List<Int>
) {
    companion object {
        const val ELEM = "stock"
        const val TABLE_NAME = "laser_$ELEM"
        const val PRIM_KEY = "id"
        const val TITRESTOCK = "titreStock"
        const val ARTICLESENSTOCKLIST = "articlesEnStockList"
    }
}
