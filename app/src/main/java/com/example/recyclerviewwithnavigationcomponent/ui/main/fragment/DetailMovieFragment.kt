package com.example.recyclerviewwithnavigationcomponent.ui.main.fragment

import android.annotation.SuppressLint
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import coil.load
import com.example.recyclerviewwithnavigationcomponent.MyApplication
import com.example.recyclerviewwithnavigationcomponent.R
import com.example.recyclerviewwithnavigationcomponent.domain.model.dataclass.DataItemCollections
import com.example.recyclerviewwithnavigationcomponent.domain.model.dataclass.Movies
import com.example.recyclerviewwithnavigationcomponent.databinding.FragmentDetailMovieBinding
import com.example.recyclerviewwithnavigationcomponent.ui.adapter.DetailTeamsRecyclerview
import com.example.recyclerviewwithnavigationcomponent.ui.main.SharedViewModel

class DetailMovieFragment : Fragment() {

    private lateinit var binding: FragmentDetailMovieBinding
    private lateinit var recyclerAdapter: DetailTeamsRecyclerview
    private lateinit var dataFavoriteMovie: Movies

    private val viewModel: SharedViewModel by activityViewModels<SharedViewModel>{
        (activity?.application as MyApplication).viewModelFactory
    }


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return FragmentDetailMovieBinding.inflate(inflater, container, false).also {
            binding = it
        }.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setDisplayDataDetailMovie()
        observeFavoriteStatus()
    }

    @SuppressLint("SetTextI18n")
    private fun setDisplayDataDetailMovie() {
        viewModel.dataCollections.observe(viewLifecycleOwner) {
            recyclerAdapter.submitList(it)
        }


        viewModel.isDataDetailError.observe(viewLifecycleOwner) {
            Toast.makeText(requireContext(), "Data is ${it.message}", Toast.LENGTH_SHORT).show()
        }

        viewModel.dataDetailMovie.observe(viewLifecycleOwner) {
            Log.d("getCollection", "dataCollection id: ${it.getCollections}")
            binding.bannerImage.load(it.backdropPath)
            binding.posterImage.load(it.posterPath)
            binding.titleMovie.text = it.originalTitle
            binding.genres.text = it.genres
            binding.releaseDate.text = it.releaseDate
            binding.duration.text = it.runtime
            binding.voteAverage.text = "Vote: ${it.voteAverage}"
            binding.detailSummary.text = it.overview
            if (it.getCollections != null) {
                viewModel.setDetailCollection(it.getCollections!!)
                binding.titleCollections.text = "${it.originalTitle} Collections"

            } else {
                viewModel.setDetailCollection(1)
                binding.titleCollections.text = "No Collections"
            }
            dataFavoriteMovie = Movies(
                id = it.id,
                originalTitle = it.originalTitle,
                image = it.posterPath,
                voteAverage = it.voteAverage,
                releaseDate = it.releaseDate
            )
            //cara makek boolean
            viewModel.checkFavoriteMovie(it.id)
        }
        recyclerviewSetup()
    }

    private fun recyclerviewSetup() {
        recyclerAdapter = DetailTeamsRecyclerview {
            itemClicked(it)
        }
        binding.rvCollections.apply {
            setHasFixedSize(true)
            layoutManager =
                LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
            adapter = recyclerAdapter
        }
    }

    private fun itemClicked(data: DataItemCollections) {
        val intent = Intent(
            Intent.ACTION_VIEW,
            Uri.parse("https://www.google.com/search?q=${data.originalTitle}")
        )
        context?.startActivity(intent)
    }

    private fun observeFavoriteStatus() {
        viewModel.isFavoriteMovieExists.observe(viewLifecycleOwner) {
            updateFavoriteButton(it)
        }
    }

    private fun updateFavoriteButton(isFavorite: Boolean) {
        if (isFavorite) {
            binding.btnFavorite.setImageDrawable(
                ContextCompat.getDrawable(
                    requireContext(),
                    R.drawable.remove_bookmark_favorite
                )
            )
            binding.btnFavorite.setOnClickListener {
                viewModel.deleteFavoriteMovie(dataFavoriteMovie.id)
                Log.d("Delete Favorite", "updateFavoriteButton: $isFavorite")
            }

        } else {
            binding.btnFavorite.setImageDrawable(
                ContextCompat.getDrawable(
                    requireContext(),
                    R.drawable.add_bookmark_favorite
                )
            )
            binding.btnFavorite.setOnClickListener {
                viewModel.insertFavoriteMovie(dataFavoriteMovie)
                Log.d("Add Favorite", "updateFavoriteButton: $isFavorite")
            }
        }
    }
}