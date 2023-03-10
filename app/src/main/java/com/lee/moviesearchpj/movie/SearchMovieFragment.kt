package com.lee.moviesearchpj.movie

import android.annotation.SuppressLint
import android.app.Application
import android.content.ContentValues.TAG
import android.content.Context.INPUT_METHOD_SERVICE
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.setFragmentResultListener
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.lee.moviesearchpj.MainActivity
import com.lee.moviesearchpj.R
import com.lee.moviesearchpj.databinding.SearchMovieFragmentBinding
import com.lee.moviesearchpj.movie.adapter.MovieRecyclerAdapter
import com.lee.moviesearchpj.movie.network.MovieService
import com.lee.moviesearchpj.movie.repository.MovieRepository
import com.lee.moviesearchpj.movie.viewmodel.MovieViewModel
import com.lee.moviesearchpj.movie.viewmodel.MovieViewModelFactory
import com.lee.moviesearchpj.serachrecord.data.Record

class SearchMovieFragment(private val application: Application):Fragment() {
    companion object{
        fun newInstance(application: Application) = SearchMovieFragment(application)
    }
    private lateinit var binding: SearchMovieFragmentBinding
    private lateinit var viewModel: MovieViewModel
    private lateinit var movieRecyclerAdapter:MovieRecyclerAdapter
    private var recordText: String = ""
    private lateinit var searchingText: String
    private var pagingCnt = 0

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View{
        binding = SearchMovieFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(this, MovieViewModelFactory(
            MovieRepository(MovieService.getInstance()), application)
        )[MovieViewModel::class.java]
        getRecord()
        observeSetup()
        listenerSetup()
    }

    private fun observeSetup(){
        viewModel.movieList.observe(viewLifecycleOwner){
            with(binding.movieRecycler){
                run{
                    recyclerSetup()
                    movieRecyclerAdapter = MovieRecyclerAdapter(it)
                    adapter = movieRecyclerAdapter
                    scrollState
                    scrollToPosition(pagingCnt*10 -5)
                }
            }
            binding.progressBar.visibility = View.GONE // rest ????????? progressbar ??????
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
    // ??? ????????? ????????? ????????? ????????? ?????? ????????? ?????????
    private fun recyclerSetup(){
        with(binding.movieRecycler){
            clearOnScrollListeners() // ????????? ???????????? ?????? ?????????
            addOnScrollListener(object: RecyclerView.OnScrollListener(){
                val totalCnt = viewModel.movieList.value?.total // data ??? ?????? ?????? ??????
                @SuppressLint("NotifyDataSetChanged")
                override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                    super.onScrolled(recyclerView, dx, dy)
                    val lastVisibleItemPosition = (recyclerView.layoutManager as LinearLayoutManager?)!!.findLastCompletelyVisibleItemPosition()
                    val itemTotalCount = recyclerView.adapter?.itemCount
                    if (totalCnt != null) {
                        // ????????? ??????, ????????? ??????????????? ????????? ???????????? ????????? ??????
                        if (lastVisibleItemPosition+1 == itemTotalCount && totalCnt - pagingCnt*10 > 10) {
                            pagingCnt ++
                            viewModel.addAllMovieFromViewModel(searchingText,(pagingCnt*10)+1)
                        }
                    }
                }
            })
        }
    }
    private fun listenerSetup() {
        with(binding){
            // ????????? ?????? ?????? ?????? ?????????
            searchText.setOnEditorActionListener { _, actionID, _ ->
                if(actionID == EditorInfo.IME_ACTION_SEARCH){
                    searchAction()
                }
                true
            }
            //?????? ?????? ?????????
            searchBtn.setOnClickListener{
                searchAction()
            }
            // ?????? ?????? ?????? ?????????
            recordBtn.setOnClickListener{
                (requireActivity() as MainActivity).changeFragment(true)
            }
        }
    }
    // ?????? ?????? ?????? ????????? ??? ?????? ??????
    // ?????? ???????????? ?????? ?????? ?????????, Text ??????, ?????????
    private fun getRecord(){
        setFragmentResultListener("selectRecord"){ _, bundle ->
            recordText= bundle.getString("recordText").toString()
            if(recordText.isNotEmpty()){ // ?????? ????????????
                pagingCnt = 0
                binding.searchText.setText(recordText)
                searchingText = recordText
                viewModel.getAllMovieFromViewModel(recordText,1)
            }
        }
    }
    //???????????? ??????
    private fun searchAction(){
        val inputMethodManager = activity?.getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
        pagingCnt = 0
        searchingText = binding.searchText.text.toString()
        // api ??????
        viewModel.getAllMovieFromViewModel(searchingText,1)
        // ????????? ??????
        if(searchingText.isNotEmpty()){
            viewModel.deleteDuplicate(Record(searchingText))
            viewModel.insertRecord(Record(searchingText))
            inputMethodManager.hideSoftInputFromWindow(binding.searchText.windowToken, 0)
        }else{ // ????????? ??? ?????????
            Toast.makeText(activity, R.string.empty_searchText,Toast.LENGTH_SHORT).show()
        }
    }
}