package com.example.mynewsapp

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.mynewsapp.model.MainViewModel
import com.example.mynewsapp.models.ApiResponse
import com.example.mynewsapp.models.RecentLink
import com.example.mynewsapp.models.TopLink
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


class TopLinks : Fragment() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var userArrayList: ArrayList<TopLink>
    private lateinit var userAdapter: NewAdapterl
    private lateinit var topLinksViewModel: MainViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_top_links, container, false)
        recyclerView = view.findViewById<RecyclerView>(R.id.reccycle1)
        recyclerView.layoutManager = LinearLayoutManager(context)
        recyclerView.setHasFixedSize(true)
        userArrayList = arrayListOf()
        userAdapter = NewAdapterl(userArrayList)
        recyclerView.adapter = userAdapter
        //EventChangelistener()
        topLinksViewModel = ViewModelProvider(this).get(MainViewModel::class.java)

        topLinksViewModel.topLinks.observe(viewLifecycleOwner, Observer { topLinks ->
            userAdapter.apply {
                clearData()
                addData(topLinks)
                notifyDataSetChanged()
            }
        })

        topLinksViewModel.error.observe(viewLifecycleOwner, Observer { error ->
        })

        topLinksViewModel.fetchData()

        return view


    }
}