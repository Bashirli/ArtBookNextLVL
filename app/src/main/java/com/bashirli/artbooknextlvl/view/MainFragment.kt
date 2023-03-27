package com.bashirli.artbooknextlvl.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.get
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.LayoutManager
import com.bashirli.artbooknextlvl.R
import com.bashirli.artbooknextlvl.adapter.ArtRecyclerAdapter
import com.bashirli.artbooknextlvl.databinding.FragmentMainBinding
import com.bashirli.artbooknextlvl.mvvm.MyMVVM
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject
@AndroidEntryPoint
class MainFragment @Inject constructor(
    var artRecyclerAdapter: ArtRecyclerAdapter

) : Fragment() {
    private lateinit var binding:FragmentMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }
    private lateinit var mainviewModel:MyMVVM

    val swipeCallback=object:ItemTouchHelper.SimpleCallback(0,ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT){
        override fun onMove(
            recyclerView: RecyclerView,
            viewHolder: RecyclerView.ViewHolder,
            target: RecyclerView.ViewHolder
        ): Boolean {
            return true
        }

        override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
            mainviewModel.deleteArt(artRecyclerAdapter.arts[viewHolder.layoutPosition])
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_main, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    binding=FragmentMainBinding.bind(view)
        binding.mainRecycler.adapter=artRecyclerAdapter
        binding.mainRecycler.layoutManager=LinearLayoutManager(requireContext())


    mainviewModel=ViewModelProvider(requireActivity()).get(MyMVVM::class.java)
        binding.fab.setOnClickListener {
            findNavController().navigate(MainFragmentDirections.actionMainFragmentToPostFragment())
        }

        ItemTouchHelper(swipeCallback).attachToRecyclerView(binding.mainRecycler)

    subObserver()
    }


    private fun subObserver(){
        mainviewModel.artList.observe(viewLifecycleOwner, Observer {
        artRecyclerAdapter.arts=it
        })
    }
}