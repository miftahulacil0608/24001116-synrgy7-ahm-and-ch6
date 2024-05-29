package com.example.recyclerviewwithnavigationcomponent.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.example.recyclerviewwithnavigationcomponent.domain.model.dataclass.Movies
import com.example.recyclerviewwithnavigationcomponent.databinding.ItemViewMovieBinding

class MovieRecyclerView(

    val itemClicked: (Movies) -> Unit
) : ListAdapter<Movies, MovieRecyclerView.ViewHolder>(
    DIFF_CALLBACK
) {

    inner class ViewHolder(private val binding: ItemViewMovieBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(data: Movies) {
            binding.imageMovie.load(data.image)
            binding.titleMovie.text = data.originalTitle
            binding.releaseDate.text = "Release Date: ${data.releaseDate}"
            binding.voteAverage.text = "Vote Average: ${data.voteAverage}"
            binding.root.setOnClickListener {
                itemClicked(data)
            }

        }
    }


    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val dataLeague = getItem(position)
        holder.bind(dataLeague)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding =
            ItemViewMovieBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    companion object {
        val DIFF_CALLBACK :DiffUtil.ItemCallback<Movies> = object : DiffUtil.ItemCallback<Movies>() {
            override fun areItemsTheSame(
                oldItem: Movies,
                newItem: Movies
            ): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(
                oldItem: Movies,
                newItem: Movies
            ): Boolean {
                return oldItem == newItem
            }


        }
    }


}