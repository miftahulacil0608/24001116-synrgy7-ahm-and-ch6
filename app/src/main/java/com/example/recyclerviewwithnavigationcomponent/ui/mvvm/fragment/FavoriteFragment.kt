package com.example.recyclerviewwithnavigationcomponent.ui.mvvm.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.recyclerviewwithnavigationcomponent.R
import com.example.recyclerviewwithnavigationcomponent.data.model.dataClass.Movies
import com.example.recyclerviewwithnavigationcomponent.databinding.FragmentFavoriteBinding
import com.example.recyclerviewwithnavigationcomponent.ui.adapter.MovieRecyclerView
import com.example.recyclerviewwithnavigationcomponent.ui.mvvm.SharedViewModel

class FavoriteFragment : Fragment() {
    private lateinit var binding: FragmentFavoriteBinding
    private val viewModel: SharedViewModel by activityViewModels {
        SharedViewModel.provideFactory(requireActivity(), requireContext())
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
            favoriteAdapter.submitList(it)
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