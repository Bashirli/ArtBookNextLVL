package com.bashirli.artbooknextlvl.room

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.bashirli.artbooknextlvl.model.Art

@Dao
interface RoomDAO {
@Insert suspend fun insert(art: Art)

@Delete suspend fun delete(art:Art)

@Query("Select * from ArtTable")
fun getAll():LiveData<List<Art>>

}