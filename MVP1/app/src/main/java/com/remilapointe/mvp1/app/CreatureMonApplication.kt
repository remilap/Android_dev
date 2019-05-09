package com.remilapointe.mvp1.app

import android.app.Application
import androidx.room.Room
import com.remilapointe.mvp1.model.room.CreatureDatabase

class CreatureMonApplication : Application() {

    companion object {
        lateinit var database: CreatureDatabase
    }

    override fun onCreate() {
        super.onCreate()

        database = Room.databaseBuilder(this, CreatureDatabase::class.java, "creature_database").build()
    }
}
