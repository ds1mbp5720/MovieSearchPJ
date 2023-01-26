package com.lee.moviesearchpj.serachrecord.adapter

import android.annotation.SuppressLint
import android.content.ContentValues.TAG
import android.util.Log
import androidx.core.os.bundleOf
import androidx.fragment.app.setFragmentResult
import androidx.recyclerview.widget.RecyclerView
import com.lee.moviesearchpj.R
import com.lee.moviesearchpj.databinding.SearchRecordRecyclerBinding
import com.lee.moviesearchpj.movie.SearchMovieFragment
import com.lee.moviesearchpj.serachrecord.SearchRecordFragment
import com.lee.moviesearchpj.serachrecord.data.Record

class RecordViewHolder(private val binding: SearchRecordRecyclerBinding): RecyclerView.ViewHolder(binding.root) {
    fun setView(searchText : String)= with(binding){
        recordText.text = searchText
    }
    @SuppressLint("UseRequireInsteadOfGet")
    fun setListener(recordList: List<Record>, position: Int, searchRecordFragment: SearchRecordFragment) = with(binding){
        root.setOnClickListener{
            // 선택한 검색어 값 SearchMovieFragment 전달
            searchRecordFragment.setFragmentResult("selectRecord", bundleOf("recordText" to recordList[position].recordText.toString()))
            // 화면 변경
            searchRecordFragment.activity?.supportFragmentManager?.beginTransaction()?.
                replace(R.id.tabContent,SearchMovieFragment(searchRecordFragment.activity!!.application))?.commit()
        }
    }
}
