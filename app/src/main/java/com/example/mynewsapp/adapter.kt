package com.example.mynewsapp

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.example.mynewsapp.models.RecentLink

class Myadapter(private val linkList: ArrayList<RecentLink>) : RecyclerView.Adapter<Myadapter.MyViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Myadapter.MyViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.listview, parent, false)
        return MyViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: Myadapter.MyViewHolder, position: Int) {
        val recentLink: RecentLink = linkList[position]
        holder.linkname.text = recentLink.app
        holder.date.text = recentLink.times_ago
        holder.linkclicks.text = recentLink.total_clicks.toString()
        holder.linkaa.text = recentLink.smart_link
    }

    fun clearData() {
        linkList.clear()
    }

    fun addData(dataList: List<RecentLink>) {
        linkList.addAll(dataList)
    }

    override fun getItemCount(): Int {
        return linkList.size
    }

    public class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val linkname: TextView = itemView.findViewById(R.id.linkname)
        val date: TextView = itemView.findViewById(R.id.date)
        val linkclicks: TextView = itemView.findViewById(R.id.linkclick)
        val linkaa: TextView = itemView.findViewById(R.id.link)
        init {
            linkaa.setOnClickListener {
                val clipboardManager =
                    itemView.context.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
                val clipData = ClipData.newPlainText("Smart Link", linkaa.text)
                clipboardManager.setPrimaryClip(clipData)
                Toast.makeText(itemView.context, "Link copied", Toast.LENGTH_SHORT).show()
            }
        }
    }
}
