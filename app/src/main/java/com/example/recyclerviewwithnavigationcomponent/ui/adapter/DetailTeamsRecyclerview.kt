package com.example.recyclerviewwithnavigationcomponent.ui.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.View.GONE
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.example.recyclerviewwithnavigationcomponent.domain.model.dataclass.DataItemCollections
import com.example.recyclerviewwithnavigationcomponent.databinding.ItemViewCollectionsBinding

class DetailTeamsRecyclerview(val itemClicked: ((DataItemCollections) -> Unit)) :
    ListAdapter<DataItemCollections, DetailTeamsRecyclerview.DetailAdapterViewHolder>(DIFF_CALLBACK) {
    inner class DetailAdapterViewHolder(private val binding: ItemViewCollectionsBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(data: DataItemCollections) {
            if (data.posterPath.isNullOrEmpty() && data.originalTitle.isNullOrEmpty()){
                binding.cardViewTeam.visibility = GONE
            }else{
                binding.posterMovie.load(data.posterPath)
                binding.titleMovieCollections.text = data.originalTitle
                binding.root.setOnClickListener {
                    itemClicked(data)
                }
            }

            Log.d("DetailAdapter", "DataItemCollection data : poster = ${data.posterPath} , title = ${data.originalTitle}")

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DetailAdapterViewHolder {
        val binding =
            ItemViewCollectionsBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return DetailAdapterViewHolder(binding)
    }

    override fun onBindViewHolder(holder: DetailAdapterViewHolder, position: Int) {
        val dataPosition = getItem(position)
        holder.bind(dataPosition)
    }

    companion object {
        val DIFF_CALLBACK = object : DiffUtil.ItemCallback<DataItemCollections>() {
            override fun areItemsTheSame(oldItem: DataItemCollections, newItem: DataItemCollections): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: DataItemCollections, newItem: DataItemCollections): Boolean {
                return oldItem == newItem
            }


        }
    }
}
