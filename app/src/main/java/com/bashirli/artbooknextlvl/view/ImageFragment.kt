package com.bashirli.artbooknextlvl.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.widget.addTextChangedListener
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.bashirli.artbooknextlvl.R
import com.bashirli.artbooknextlvl.adapter.PublishRecyclerAdapter
import com.bashirli.artbooknextlvl.databinding.FragmentImageBinding
import com.bashirli.artbooknextlvl.mvvm.MyMVVM
import com.bashirli.artbooknextlvl.util.Status
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject
@AndroidEntryPoint
class ImageFragment @Inject constructor(
    private val publishRecyclerAdapter: PublishRecyclerAdapter
) : Fragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }
    private lateinit var viewModel:MyMVVM
    lateinit var binding:FragmentImageBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_image, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel=ViewModelProvider(requireActivity()).get(MyMVVM::class.java)
        binding= FragmentImageBinding.bind(view)
        binding.imageRecycler.adapter=publishRecyclerAdapter
        binding.imageRecycler.layoutManager=GridLayoutManager(requireContext(),4)
        publishRecyclerAdapter.setOnItemClickListener {
            findNavController().popBackStack()
            viewModel.setSelectedImage(it)
        }
        var job:Job?=null
        binding.editTextTextPersonName3.addTextChangedListener {
            job?.cancel()
            job=lifecycleScope.launch {
                delay(1000)
                it?.let {
                    if(it.toString().isNotEmpty()){
                        viewModel.searchImage(it.toString())
                    }
                }
            }
        }

    subToObs()

    }

    fun subToObs(){
        viewModel.imageList.observe(viewLifecycleOwner, Observer {
        when(it.status){
            Status.SUCCESS->{
                val urls=it.data?.hits?.map {
                    it.previewURL
                }
                publishRecyclerAdapter.images=urls?: listOf()
                binding.progressBar.visibility=View.GONE
            }
            Status.ERROR->{
                Toast.makeText(requireContext(),it.message?:"Error",Toast.LENGTH_SHORT).show()
                binding.progressBar.visibility=View.GONE
            }
            Status.LOADING->{
                binding.progressBar.visibility=View.VISIBLE
            }
        }

        })
    }
}