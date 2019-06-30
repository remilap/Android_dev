package com.remilapointe.laser.db

import androidx.room.PrimaryKey

open class GenericStringTable(
    @PrimaryKey
    open val elem: String
) {

}