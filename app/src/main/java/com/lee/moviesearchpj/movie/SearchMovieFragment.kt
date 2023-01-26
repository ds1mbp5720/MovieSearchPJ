package com.lee.moviesearchpj.movie

import android.content.ContentValues.TAG
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.lee.moviesearchpj.R
import com.lee.moviesearchpj.databinding.SearchMovieFragmentBinding
import com.lee.moviesearchpj.movie.adapter.MovieRecyclerAdapter
import com.lee.moviesearchpj.movie.network.MovieService
import com.lee.moviesearchpj.serachrecord.SearchRecordFragment

class SearchMovieFragment:Fragment() {
    companion object{
        fun newInstance() = SearchMovieFragment()
    }
    private lateinit var binding: SearchMovieFragmentBinding
    private lateinit var viewModel: MovieViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = SearchMovieFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(this,MovieViewModelFactory(
            MovieRepository(MovieService.getInstance())
        ))[MovieViewModel::class.java]

        observeSetup()
        listenerSetup()

    }
    private fun observeSetup(){
        viewModel.movieList.observe(viewLifecycleOwner){
            with(binding.movieRecycler){
                run{
                    val poiAdapter = MovieRecyclerAdapter(it,this@SearchMovieFragment)
                    adapter = poiAdapter
                }
            }
            binding.progressBar.visibility = View.GONE // rest 완료시 progressbar 제거
        }
        viewModel.errorMessage.observe(viewLifecycleOwner){
            Log.e(TAG,it)
        }
        viewModel.isLoading.observe(viewLifecycleOwner){
            if(it){
                binding.progressBar.visibility = View.VISIBLE
            }else{
                binding.progressBar.visibility = View.GONE
            }
        }
    }
    // 리스너 셋팅 함수
    private fun listenerSetup() {
        with(binding){
            searchBtn.setOnClickListener{
                viewModel.getAllMovieFromViewModel(binding.searchText.text.toString(),1)
            }
            recordBtn.setOnClickListener{
                activity?.supportFragmentManager?.beginTransaction()
                    ?.replace(R.id.tabContent,SearchRecordFragment.newInstance())?.addToBackStack(null)?.commit()
            }
        }
    }
}