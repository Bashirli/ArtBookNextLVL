package com.bashirli.artbooknextlvl.repo

import androidx.lifecycle.LiveData
import com.bashirli.artbooknextlvl.model.Art
import com.bashirli.artbooknextlvl.model.RetrofitData
import com.bashirli.artbooknextlvl.room.RoomDAO
import com.bashirli.artbooknextlvl.room.RoomDB
import com.bashirli.artbooknextlvl.service.RetrofitAPI
import com.bashirli.artbooknextlvl.util.Resource
import retrofit2.Response
import javax.inject.Inject

class ArtRepo @Inject constructor(
    val roomDAO: RoomDAO,
    val retrofitAPI: RetrofitAPI
) : ArtInterface {
    override suspend fun insertArt(art: Art) {
    roomDAO.insert(art)
    }

    override suspend fun deleteArt(art: Art) {
    roomDAO.delete(art)
    }

    override fun getArt(): LiveData<List<Art>> {
    return roomDAO.getAll()
    }

    override suspend fun searchImage(string: String): Resource<RetrofitData> {
        return try {
            val response = retrofitAPI.imageSearch(string)
            if (response.isSuccessful) {

                response.body()?.let {
                    return@let Resource.success(it)
                } ?: Resource.error("Error", null)
            } else {
                Resource.error("Error",null)
              }
        }catch (e:Exception){
            Resource.error(e.localizedMessage,null)
        }
    }
}