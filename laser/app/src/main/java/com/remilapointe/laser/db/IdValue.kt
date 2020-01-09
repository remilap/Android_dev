package com.remilapointe.laser.db

interface IdValue {
    val id: Int
    val elem: String
    fun getString() : String
}
