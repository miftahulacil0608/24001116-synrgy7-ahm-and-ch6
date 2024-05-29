package com.example.recyclerviewwithnavigationcomponent.ui.mvvm.fragment

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.Toast

import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.recyclerviewwithnavigationcomponent.R
import com.example.recyclerviewwithnavigationcomponent.domain.model.dataclass.Movies
import com.example.recyclerviewwithnavigationcomponent.databinding.FragmentMoviesBinding
import com.example.recyclerviewwithnavigationcomponent.ui.adapter.MovieRecyclerView
import com.example.recyclerviewwithnavigationcomponent.ui.authenctication.AuthenticationActivity
import com.example.recyclerviewwithnavigationcomponent.ui.mvvm.SharedViewModel

//view
@Suppress("DEPRECATION")
class MoviesFragment : Fragment() {
    private lateinit var binding: FragmentMoviesBinding

    private val viewModel: SharedViewModel by activityViewModels {
        SharedViewModel.provideFactory(this, requireContext())
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return FragmentMoviesBinding.inflate(inflater, container, false).also {
            binding = it
        }.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        showRecyclerView()
        logout()
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setHasOptionsMenu(true)
    }

    @Deprecated("Deprecated in Java", ReplaceWith(
        "inflater.inflate(R.menu.menu_option, menu)",
        "com.example.recyclerviewwithnavigationcomponent.R"
    )
    )
    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu_option, menu)
    }


    @Deprecated("Deprecated in Java")
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.btn_profile->{
                findNavController().navigate(R.id.action_moviesFragment_to_profileFragment)
            true
            }
            R.id.btn_favorite ->{
                findNavController().navigate(R.id.action_moviesFragment_to_favoriteFragment)
                true
            }
            else -> super.onOptionsItemSelected(item)

        }
    }

    private fun showRecyclerView() {
        val movieRecyclerViewAdapter =
            MovieRecyclerView(
                itemClicked = { clicked(it) },
            )
        viewModel.dataMovie.observe(viewLifecycleOwner){
            movieRecyclerViewAdapter.submitList(it)
        }

        viewModel.isDataMovieError.observe(viewLifecycleOwner){
            Toast.makeText(requireContext(), "${it.message}", Toast.LENGTH_SHORT).show()
        }

        binding.rvListLeagueGroup.setHasFixedSize(true)
        binding.rvListLeagueGroup.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        binding.rvListLeagueGroup.adapter = movieRecyclerViewAdapter
        binding.rvListLeagueGroup.itemAnimator = null

    }

    private fun logout() {
        viewModel.successLogout.observe(viewLifecycleOwner) { isLogout ->
            if (isLogout.equals(true)) {
                startActivity(Intent(requireActivity(), AuthenticationActivity::class.java))
                requireActivity().finish()
            }
        }
    }


    private fun clicked(data: Movies) {
        findNavController().navigate(
            R.id.action_moviesFragment_to_detailMovie
        )
            .also {
                viewModel.setDetailMovie(data.id)
            }
    }


}




