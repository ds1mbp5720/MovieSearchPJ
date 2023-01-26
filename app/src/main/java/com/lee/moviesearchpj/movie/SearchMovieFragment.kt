package com.lee.moviesearchpj.movie

import android.app.Application
import android.content.ContentValues.TAG
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.Fragment
import androidx.fragment.app.setFragmentResultListener
import androidx.lifecycle.ViewModelProvider
import com.lee.moviesearchpj.R
import com.lee.moviesearchpj.databinding.SearchMovieFragmentBinding
import com.lee.moviesearchpj.movie.adapter.MovieRecyclerAdapter
import com.lee.moviesearchpj.movie.network.MovieService
import com.lee.moviesearchpj.movie.repository.MovieRepository
import com.lee.moviesearchpj.movie.viewmodel.MovieViewModel
import com.lee.moviesearchpj.movie.viewmodel.MovieViewModelFactory
import com.lee.moviesearchpj.serachrecord.SearchRecordFragment
import com.lee.moviesearchpj.serachrecord.data.Record

class SearchMovieFragment(var application: Application):Fragment() {
    companion object{
        fun newInstance(application: Application) = SearchMovieFragment(application)
    }
    private lateinit var binding: SearchMovieFragmentBinding
    private lateinit var viewModel: MovieViewModel
    private var recordText: String = ""

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
        viewModel = ViewModelProvider(this, MovieViewModelFactory(
            MovieRepository(MovieService.getInstance()), application
        )
        )[MovieViewModel::class.java]
        getRecord()
        observeSetup()
        listenerSetup()
        pressBackKey()
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

    private fun listenerSetup() {
        with(binding){
            //검색 버튼 이벤트
            searchBtn.setOnClickListener{
                val searchText = binding.searchText.text.toString()
                // api 통신
                viewModel.getAllMovieFromViewModel(searchText,1)

                // 검색어 저장
                if(searchText.isNotEmpty()){
                    viewModel.insertRecord(Record(searchText))
                }else{ // 검색어 미 입력시
                    Toast.makeText(activity,"검색어를 입력하세요.",Toast.LENGTH_SHORT).show()
                }
            }
            // 최근 검색 버튼 이벤트
            recordBtn.setOnClickListener{
                // 최근 기록 fragment 화면으로 변경
                activity?.supportFragmentManager?.beginTransaction()
                    ?.replace(R.id.tabContent,SearchRecordFragment.newInstance())?.addToBackStack(null)?.commit()
            }
        }
    }
    // 최근 검색 버튼 클릭시 값 수신 함수
    private fun getRecord(){
        setFragmentResultListener("selectRecord"){ _, bundle ->
            recordText= bundle.getString("recordText").toString()
            if(recordText.isNotEmpty()){ // 값을 받을경우
                binding.searchText.setText(recordText)
                // api 통신
                viewModel.getAllMovieFromViewModel(recordText,1)
            }
        }
    }
    // app 완전 종료 버튼
    private fun pressBackKey(){
        activity?.onBackPressedDispatcher?.addCallback(object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                activity!!.finish()
            }
        })
    }
}