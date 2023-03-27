package com.bashirli.artbooknextlvl.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bashirli.artbooknextlvl.databinding.RecyclerpostBinding
import com.bashirli.artbooknextlvl.model.Art
import com.bumptech.glide.Glide
import com.bumptech.glide.RequestManager
import javax.inject.Inject

class ArtRecyclerAdapter @Inject constructor(
    var glide:RequestManager

) : RecyclerView.Adapter<ArtRecyclerAdapter.ArtHolder>() {
    class ArtHolder(var binding:RecyclerpostBinding) : RecyclerView.ViewHolder(binding.root)

    private val diffUtil=object: DiffUtil.ItemCallback<Art>(){
        override fun areItemsTheSame(oldItem: Art, newItem: Art): Boolean {
            return oldItem==newItem
        }

        override fun areContentsTheSame(oldItem: Art, newItem: Art): Boolean {
            return oldItem==newItem
        }

    }

    private val recyclerDiff=AsyncListDiffer(this,diffUtil)
    var arts:List<Art>
    get()=recyclerDiff.currentList
    set(value) = recyclerDiff.submitList(value)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ArtHolder {
       val binding=RecyclerpostBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return ArtHolder(binding)
    }

    override fun onBindViewHolder(holder: ArtHolder, position: Int) {
       holder.binding.textView.setText("Name : ${arts[position].name}")
        holder.binding.textView2.setText("Surname : ${arts[position].surname}")
        glide.load(arts[position].imageUrl).into(holder.binding.imageView2)
    }

    override fun getItemCount(): Int {
        return arts.size
    }
}