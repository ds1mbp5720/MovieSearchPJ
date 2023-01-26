package com.lee.moviesearchpj.movie.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.lee.moviesearchpj.databinding.MovieInfoRecyclerBinding
import com.lee.moviesearchpj.movie.SearchMovieFragment
import com.lee.moviesearchpj.movie.data.MovieData

class MovieRecyclerAdapter(private val movieData: MovieData, private val owner:SearchMovieFragment):RecyclerView.Adapter<MovieViewHolder>() {
    private lateinit var binding: MovieInfoRecyclerBinding

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder {
        binding = MovieInfoRecyclerBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MovieViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
        val movie = movieData.items[position]
        holder.setView(movie)
        holder.setListener(movie.link) // 상세보기 연결
    }

    override fun getItemCount() = movieData.items.size
}