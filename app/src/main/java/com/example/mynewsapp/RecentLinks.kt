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
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


class RecentLinks : Fragment() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var userArrayList: ArrayList<RecentLink>
    private lateinit var userAdapter: Myadapter
    private lateinit var recentLinksViewModel: MainViewModel

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
        userAdapter = Myadapter(userArrayList)
        recyclerView.adapter = userAdapter

        recentLinksViewModel = ViewModelProvider(this).get(MainViewModel::class.java)

        recentLinksViewModel.recentLinks.observe(viewLifecycleOwner, Observer { recentLinks ->
            userAdapter.apply {
                clearData()
                addData(recentLinks)
                notifyDataSetChanged()
            }
        })

        recentLinksViewModel.error.observe(viewLifecycleOwner, Observer { error ->
        })

        recentLinksViewModel.fetchData()




        return view


    }

}