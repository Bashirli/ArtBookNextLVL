package com.bashirli.artbooknextlvl.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.bashirli.artbooknextlvl.R
import com.bashirli.artbooknextlvl.databinding.FragmentPostBinding
import com.bashirli.artbooknextlvl.mvvm.MyMVVM
import com.bashirli.artbooknextlvl.util.Status
import com.bumptech.glide.RequestManager
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject
@AndroidEntryPoint
class PostFragment @Inject constructor(
    private val glide:RequestManager
) : Fragment() {
    private lateinit var binding:FragmentPostBinding
    lateinit var viewModel: MyMVVM
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_post, container, false)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel=ViewModelProvider(requireActivity()).get(MyMVVM::class.java)
    binding=FragmentPostBinding.bind(view)

        binding.imageView.setOnClickListener {
            findNavController().navigate(PostFragmentDirections.actionPostFragmentToImageFragment())
        }

        binding.button.setOnClickListener {
            viewModel.makeArt(binding.editTextTextPersonName.text.toString(),binding.editTextTextPersonName2.text.toString())

        }
        subsObserver()
    }

    private fun subsObserver(){
        viewModel.selectedImageURL.observe(viewLifecycleOwner, Observer { url->
            binding.let {
                glide.load(url).into(it.imageView)
            }

        })

        viewModel.insertArtMSG.observe(viewLifecycleOwner, Observer {
            when(it.status){
                Status.SUCCESS-> {
                    Toast.makeText(requireContext(), "Scs", Toast.LENGTH_SHORT).show()
                    findNavController().popBackStack()
                    viewModel.resetMSG()
                }
                    Status.ERROR->{
                        Toast.makeText(requireContext(), it.message?:"Error", Toast.LENGTH_SHORT).show()
                    }
                Status.LOADING->{

                }
            }
        })

    }


}