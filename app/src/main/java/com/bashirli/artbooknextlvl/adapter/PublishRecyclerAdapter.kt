package com.bashirli.artbooknextlvl.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.Recycler
import com.bashirli.artbooknextlvl.databinding.RecyclerimageBinding
import com.bashirli.artbooknextlvl.databinding.RecyclerpostBinding
import com.bashirli.artbooknextlvl.model.Art
import com.bumptech.glide.RequestManager
import javax.inject.Inject

class PublishRecyclerAdapter @Inject constructor(
    var glide:RequestManager
) :
    RecyclerView.Adapter<PublishRecyclerAdapter.PublishHolder>() {
    class PublishHolder(var binding:RecyclerimageBinding) : RecyclerView.ViewHolder(binding.root){

    }
    private val diffUtil=object: DiffUtil.ItemCallback<String>(){
        override fun areItemsTheSame(oldItem: String, newItem: String): Boolean {
            return oldItem==newItem
        }

        override fun areContentsTheSame(oldItem: String, newItem: String): Boolean {
            return oldItem==newItem
        }

    }

    var onClickItemListener: ((String)->Unit)?=null

    fun setOnItemClickListener(listener:((String)->Unit)){
        onClickItemListener=listener
    }

    private val recyclerDiff= AsyncListDiffer(this,diffUtil)
    var images:List<String>
        get()=recyclerDiff.currentList
        set(value) = recyclerDiff.submitList(value)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PublishHolder {
        var binding=RecyclerimageBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return PublishHolder(binding)
    }

    override fun onBindViewHolder(holder: PublishHolder, position: Int) {
        holder.itemView.apply {
            glide.load(images[position]).into(holder.binding.imageView3)
        setOnClickListener{
            onClickItemListener?.let {
                it(images[position])
            }
        }

        }
    }

    override fun getItemCount(): Int {
        return images.size
    }
}