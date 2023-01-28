package com.lee.moviesearchpj

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.OnBackPressedCallback
import com.lee.moviesearchpj.databinding.ActivityMainBinding
import com.lee.moviesearchpj.movie.SearchMovieFragment
import com.lee.moviesearchpj.serachrecord.SearchRecordFragment

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var searchMovieFragment: SearchMovieFragment
    private lateinit var searchRecordFragment: SearchRecordFragment

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater).also {
            setContentView(it.root)
        }
        createFragment(savedInstanceState)
        //최초 화면
        supportFragmentManager.beginTransaction().add(R.id.tabContent,searchMovieFragment).add(R.id.tabContent,searchRecordFragment)
            .hide(searchRecordFragment).commit()
        pressBackKey(false)
    }
    private fun createFragment(savedInstanceState: Bundle?){
        if(savedInstanceState == null){
            searchMovieFragment = SearchMovieFragment.newInstance(this.application)
            searchRecordFragment= SearchRecordFragment.newInstance()
        }
    }

    fun changeFragment(signal: Boolean){
        if(signal){ // 최근검색 버튼 터치
            supportFragmentManager.beginTransaction().show(searchRecordFragment).hide(searchMovieFragment).commit()
            pressBackKey(true)

        }else{ // 최근검색 단어 터치
            supportFragmentManager.beginTransaction().show(searchMovieFragment).hide(searchRecordFragment).commit()
            pressBackKey(false)
        }
    }
    // 상황별 back key 설정 함수
    private fun pressBackKey(signal: Boolean){
        onBackPressedDispatcher.addCallback(object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                if(signal){ // SearchRecordFragment 이동
                    supportFragmentManager.beginTransaction().show(searchMovieFragment).hide(searchRecordFragment).commit()
                    pressBackKey(false)
                }else{
                    finish()
                }
            }
        })
    }
}