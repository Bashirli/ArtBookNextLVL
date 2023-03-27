package com.bashirli.artbooknextlvl

import android.app.DownloadManager.Request
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentFactory
import com.bashirli.artbooknextlvl.adapter.ArtRecyclerAdapter
import com.bashirli.artbooknextlvl.adapter.PublishRecyclerAdapter
import com.bashirli.artbooknextlvl.view.ImageFragment
import com.bashirli.artbooknextlvl.view.MainFragment
import com.bashirli.artbooknextlvl.view.PostFragment
import com.bumptech.glide.RequestManager
import dagger.hilt.android.components.FragmentComponent
import javax.inject.Inject

class ArtFragmentFactory @Inject constructor(
    private val glide:RequestManager,
    private val artRecyclerAdapter: ArtRecyclerAdapter,
    private val publishRecyclerAdapter: PublishRecyclerAdapter
) : FragmentFactory() {

    override fun instantiate(classLoader: ClassLoader, className: String): Fragment {
        return when(className){
            PostFragment::class.java.name->PostFragment(glide)
            MainFragment::class.java.name->MainFragment(artRecyclerAdapter)
            ImageFragment::class.java.name->ImageFragment(publishRecyclerAdapter)
            else->return super.instantiate(classLoader, className)
        }


    }
}