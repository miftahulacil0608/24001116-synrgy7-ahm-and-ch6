package com.example.recyclerviewwithnavigationcomponent.ui.main.fragment

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.recyclerviewwithnavigationcomponent.MyApplication
import com.example.recyclerviewwithnavigationcomponent.R
import com.example.recyclerviewwithnavigationcomponent.domain.model.dataclass.Movies
import com.example.recyclerviewwithnavigationcomponent.databinding.FragmentFavoriteBinding
import com.example.recyclerviewwithnavigationcomponent.ui.adapter.MovieRecyclerView
import com.example.recyclerviewwithnavigationcomponent.ui.main.SharedViewModel

class FavoriteFragment : Fragment() {
    private lateinit var binding: FragmentFavoriteBinding
    private val viewModel: SharedViewModel by activityViewModels<SharedViewModel>{
        (activity?.application as MyApplication).viewModelFactory
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentFavoriteBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val favoriteAdapter = MovieRecyclerView(itemClicked = {
            itemClicked(it)
        })

        viewModel.dataFavoriteMovie.observe(viewLifecycleOwner) {
            it.forEach{
                Log.d("data favorite", "favorite movie: $it")
            }
            favoriteAdapter.submitList(it)
        }

        viewModel.isDataFavoriteMovieError.observe(viewLifecycleOwner){
            Toast.makeText(requireContext(), "${it.message}", Toast.LENGTH_SHORT).show()
        }

        binding.rvFavorite.apply {
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(context)
            adapter = favoriteAdapter
        }
    }

    private fun itemClicked(data: Movies) {
        findNavController().navigate(R.id.action_favoriteFragment_to_detailMovie).also {
            viewModel.setDetailMovie(data.id)
        }
    }


}