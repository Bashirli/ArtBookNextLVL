package com.bashirli.artbooknextlvl.mvvm

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bashirli.artbooknextlvl.model.Art
import com.bashirli.artbooknextlvl.model.RetrofitData
import com.bashirli.artbooknextlvl.repo.ArtInterface
import com.bashirli.artbooknextlvl.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import retrofit2.Response
import javax.inject.Inject
@HiltViewModel
public class MyMVVM @Inject constructor(
    private val repository:ArtInterface
) : ViewModel(){

    //MainFrag
    val artList=repository.getArt()

    //PostFragment
    private var insertArtMessage= MutableLiveData<Resource<Art>>()
    val insertArtMSG:LiveData<Resource<Art>>
        get() {
            return insertArtMessage
        }

    fun resetMSG(){
        insertArtMessage= MutableLiveData<Resource<Art>>()
    }


    fun setSelectedImage(url:String){
        selectedImage.postValue(url)
    }

    fun deleteArt(art:Art){
        viewModelScope.launch {
            repository.deleteArt(art)
        }
    }
    fun insertArt(art:Art){
        viewModelScope.launch {
            repository.insertArt(art)
        }
    }

    fun searchImage(string: String){
        if(string.isEmpty()){
            return
        }
    images.value=Resource.loading(null)
    viewModelScope.launch {
        val response=repository.searchImage(string)
        images.value=response
    }


    }

    fun makeArt(name:String,surname:String){
    if(name.isEmpty()||surname.isEmpty()){
        insertArtMessage.value=Resource.error("Enter name or surname",null)
        return
    }

        val art=Art(name,surname,selectedImage.value?:"empty")
        insertArt(art)
        setSelectedImage("")
        insertArtMessage.postValue(Resource.success(null))
    }



    //ImageFragment
    private val images= MutableLiveData<Resource<RetrofitData>>()
    val imageList : LiveData<Resource<RetrofitData>>
        get() {
            return images
        }

        private val selectedImage= MutableLiveData<String>()
        val selectedImageURL:LiveData<String>
            get() {
                return selectedImage
            }

}