package com.lee.moviesearchpj

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.lee.moviesearchpj.databinding.ActivityMainBinding
import com.lee.moviesearchpj.movie.SearchMovieFragment

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var searchMovieFragment: SearchMovieFragment

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater).also {
            setContentView(it.root)
        }
        createFragment(savedInstanceState)
        //최초 화면
        supportFragmentManager.beginTransaction().add(R.id.tabContent,searchMovieFragment).commit()
    }

    private fun createFragment(savedInstanceState: Bundle?){
        if(savedInstanceState == null){
            searchMovieFragment = SearchMovieFragment(this.application)
        }
    }
}