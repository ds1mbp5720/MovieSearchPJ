package com.lee.moviesearchpj.movie.adapter

import android.annotation.SuppressLint
import android.content.Intent
import android.net.Uri
import androidx.core.content.ContextCompat.startActivity
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.lee.moviesearchpj.databinding.MovieInfoRecyclerBinding
import com.lee.moviesearchpj.movie.data.MovieData


class MovieViewHolder(private val binding: MovieInfoRecyclerBinding): RecyclerView.ViewHolder(binding.root) {
    @SuppressLint("SetTextI18n")
    fun setView(movie : MovieData.Item)= with(binding){
        moviePoster
        movieTitle.text = "제목: " + filterTitleText(movie.title)
        movieDate.text = "출시일: " + movie.pubDate
        movieScore.text = "평점: " + movie.userRating
        Glide.with(this.moviePoster.context).load(movie.image).into(this.moviePoster)// 이미지 처리
    }
    // 포스터 상세보기 이동 정의
    fun setListener(link: String) = with(binding){
        root.setOnClickListener{
            val intent = Intent(Intent.ACTION_VIEW)
            intent.data = Uri.parse("$link/")
            startActivity(itemView.context,intent,null)
        }
    }
    //영화 제목 <b> </b> 제거
    private fun filterTitleText(title: String):String{
        var combineString = ""
        return if(title.contains("<b>")){
            val  string = title.split("<b>","</b>")
            for (element in string){
                combineString += element
            }
            combineString
        } else title
    }
}