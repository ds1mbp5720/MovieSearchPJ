package com.lee.moviesearchpj

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.lee.moviesearchpj.databinding.ActivityMainBinding
import com.lee.moviesearchpj.movie.SearchMovieFragment
import com.lee.moviesearchpj.serachrecord.SearchRecordFragment

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var searchMovieFragment: SearchMovieFragment

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater).also {
            setContentView(it.root)
        }
        createFragment(savedInstanceState)

        supportFragmentManager.beginTransaction().add(R.id.tabContent,searchMovieFragment).commit()
        //초기 보여질 화면
    }
    private fun createFragment(savedInstanceState: Bundle?){
        if(savedInstanceState == null){
            searchMovieFragment = SearchMovieFragment()
        }
    }
}