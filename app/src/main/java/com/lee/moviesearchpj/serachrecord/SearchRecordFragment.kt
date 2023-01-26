package com.lee.moviesearchpj.serachrecord

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.lee.moviesearchpj.databinding.SearchRecordFragmentBinding

class SearchRecordFragment:Fragment() {
    companion object{
        fun newInstance() = SearchRecordFragment()
    }
    private lateinit var binding: SearchRecordFragmentBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = SearchRecordFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }
}