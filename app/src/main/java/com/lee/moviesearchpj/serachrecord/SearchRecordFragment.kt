package com.lee.moviesearchpj.serachrecord

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.GridLayoutManager
import com.lee.moviesearchpj.databinding.SearchRecordFragmentBinding
import com.lee.moviesearchpj.serachrecord.adapter.RecordRecyclerAdapter
import com.lee.moviesearchpj.serachrecord.viewmodel.RecordViewModel


class SearchRecordFragment:Fragment() {
    companion object{
        fun newInstance() = SearchRecordFragment()
    }
    val viewModel: RecordViewModel by viewModels()
    private lateinit var binding: SearchRecordFragmentBinding
    private var recordAdapter: RecordRecyclerAdapter? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = SearchRecordFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        observerSetup()
        uiSetup()
    }

    private fun observerSetup(){
        viewModel.getAllRecords()?.observe(viewLifecycleOwner){ products ->
            products?.let {
                recordAdapter?.setRecordsList(it)
            }
        }
    }

    private fun uiSetup(){
        val gridLayoutManager = GridLayoutManager(this.context, 3)
        with(binding.recordRecycler){
            layoutManager = gridLayoutManager
            recordAdapter = RecordRecyclerAdapter(this@SearchRecordFragment)
            adapter = recordAdapter
        }
    }
}