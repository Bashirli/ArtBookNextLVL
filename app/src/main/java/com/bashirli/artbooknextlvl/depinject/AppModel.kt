package com.bashirli.artbooknextlvl.depinject

import android.content.Context
import androidx.room.Room
import androidx.room.RoomDatabase
import com.bashirli.artbooknextlvl.R
import com.bashirli.artbooknextlvl.repo.ArtInterface
import com.bashirli.artbooknextlvl.repo.ArtRepo
import com.bashirli.artbooknextlvl.room.RoomDAO
import com.bashirli.artbooknextlvl.room.RoomDB
import com.bashirli.artbooknextlvl.service.RetrofitAPI
import com.bashirli.artbooknextlvl.util.Util.BASE_URL
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object AppModel {
    @Singleton
    @Provides
    fun injectRoomDB(
        @ApplicationContext context: Context
    ):RoomDB{
        return Room.databaseBuilder(context,RoomDB::class.java,"ArtDB").build()
    }

    @Singleton
    @Provides
    fun injectDAO(database:RoomDB)=database.getDao()

    @Singleton
    @Provides
    fun injectRetrofitAPI():RetrofitAPI{
        return Retrofit.Builder().addConverterFactory(GsonConverterFactory.create())
            .baseUrl(BASE_URL).build().create(RetrofitAPI::class.java)
    }


    @Singleton
    @Provides
    fun injectGlide(@ApplicationContext context: Context)=Glide.with(context).setDefaultRequestOptions(
        RequestOptions().placeholder(R.drawable.ic_launcher_background)
            .error(R.drawable.ic_launcher_background)
    )


    @Singleton
    @Provides
    fun injectRepo(dao:RoomDAO,api:RetrofitAPI)=ArtRepo(dao,api) as ArtInterface

}