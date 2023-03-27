package com.bashirli.artbooknextlvl.room

import androidx.room.Database
import androidx.room.RoomDatabase
import com.bashirli.artbooknextlvl.model.Art

@Database(entities = arrayOf(Art::class), version = 1)
abstract class RoomDB : RoomDatabase() {
    abstract fun getDao():RoomDAO
}