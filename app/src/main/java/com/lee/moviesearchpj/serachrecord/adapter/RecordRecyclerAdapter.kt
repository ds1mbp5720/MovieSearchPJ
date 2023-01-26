package com.lee.moviesearchpj.serachrecord.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.lee.moviesearchpj.databinding.SearchRecordRecyclerBinding
import com.lee.moviesearchpj.serachrecord.SearchRecordFragment
import com.lee.moviesearchpj.serachrecord.data.Record

class RecordRecyclerAdapter(private val searchRecordFragment: SearchRecordFragment): RecyclerView.Adapter<RecordViewHolder>(){
    var recordList: MutableList<Record>? = null
    private lateinit var binding: SearchRecordRecyclerBinding

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecordViewHolder {
        binding = SearchRecordRecyclerBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        checkRecordCnt()
        return RecordViewHolder(binding)
    }

    override fun onBindViewHolder(holder: RecordViewHolder, listPosition: Int) {
        recordList.let {
            holder.setView(it!![listPosition].recordText.toString())
            holder.setListener(it, listPosition, searchRecordFragment)
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    fun setProductList(products: List<Record>){
        recordList = products.toMutableList()
        notifyDataSetChanged()
    }

    // 검색기록 10개 이상시 삭제 함수
    private fun checkRecordCnt(){
        if(itemCount > 10){
            searchRecordFragment.viewModel.deleteProduct(recordList?.get(0)?.recordText.toString())
        }
    }
    override fun getItemCount() = if(recordList == null) 0 else recordList!!.size
}