package com.bashirli.artbooknextlvl.repo

import androidx.lifecycle.LiveData
import com.bashirli.artbooknextlvl.model.Art
import com.bashirli.artbooknextlvl.model.RetrofitData
import com.bashirli.artbooknextlvl.util.Resource

interface ArtInterface {
    suspend fun insertArt(art: Art)

    suspend fun deleteArt(art: Art)

    fun getArt() : LiveData<List<Art>>

    suspend fun searchImage(string: String):Resource<RetrofitData>

}