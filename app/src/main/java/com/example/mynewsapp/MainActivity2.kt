package com.example.mynewsapp

import android.content.Context
import android.graphics.Color
import android.os.Bundle
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager.widget.ViewPager
import com.example.mynewsapp.model.MainViewModel
import com.example.mynewsapp.models.ApiResponse
import com.example.mynewsapp.models.RecentLink
import com.github.mikephil.charting.charts.LineChart
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet
import com.github.mikephil.charting.formatter.ValueFormatter
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet
import com.google.android.material.tabs.TabLayout
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.time.LocalTime


class MainActivity2 : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main2)
        val clicks =findViewById<TextView>(R.id.cc)
        val toploactions =findViewById<TextView>(R.id.ccdd)
        val topsources =findViewById<TextView>(R.id.ccbbd)
        val extraearning =findViewById<TextView>(R.id.ccaa)

        var viewPager = findViewById(R.id.viewPager) as ViewPager
        var tablayout = findViewById(R.id.tabLayout) as TabLayout

        val greetingTextView: TextView = findViewById(R.id.morning)
        val currentTime = LocalTime.now()
        val greetingMessage = when (currentTime.hour) {
            in 0..11 -> "Good morning!"
            in 12..16 -> "Good afternoon!"
            in 17..20 -> "Good evening!"
            else -> "Good night!"
        }
        greetingTextView.text = greetingMessage
        val viewModel = ViewModelProvider(this).get(MainViewModel::class.java)
        viewModel.fetchData()


        val fragmentAdapter = fragmentadapter(supportFragmentManager)
        fragmentAdapter.addFragment(TopLinks(),"TOP LINKS")
        fragmentAdapter.addFragment(RecentLinks(),"RECENT LINKS")



        viewPager.adapter = fragmentAdapter
        tablayout.setupWithViewPager(viewPager)

        viewModel.totalClicks.observe(this, { totalClicks ->
            clicks.text = totalClicks.toString()
        })

        viewModel.totalLinks.observe(this, { totalLinks ->
            extraearning.text = totalLinks.toString()
        })

        viewModel.topSource.observe(this, { topSource ->
            topsources.text = topSource
        })

        viewModel.topLocation.observe(this, { topLocation ->
            toploactions.text = topLocation
        })

        viewModel.overallUrlChart.observe(this, { overallUrlChart ->
            if (overallUrlChart != null) {
                displayDataInChart(overallUrlChart)
            }
        })
    }

    private fun displayDataInChart(chartData: Map<String, Int>) {
        val entries = ArrayList<Entry>()

        var index = 1
        for ((date, value) in chartData) {
            entries.add(Entry(index.toFloat(), value.toFloat()))
            index++
        }

        val dataSet = LineDataSet(entries, "Clicks")
        dataSet.color = Color.BLUE
        dataSet.lineWidth = 2f
        dataSet.setDrawCircles(false)
        dataSet.setDrawFilled(true)
        dataSet.fillColor = Color.BLUE
        dataSet.fillAlpha = 20
        dataSet.setDrawValues(false)

        val lineDataSets = ArrayList<ILineDataSet>()
        lineDataSets.add(dataSet)

        val lineData = LineData(lineDataSets)

        val lineChart = findViewById<LineChart>(R.id.linechart2)
        lineChart.data = lineData
        lineChart.xAxis.position = XAxis.XAxisPosition.BOTTOM
        lineChart.xAxis.setDrawGridLines(true)
        lineChart.xAxis.granularity = 1f
        lineChart.xAxis.valueFormatter = DateValueFormatter(chartData.keys.toTypedArray())
        lineChart.axisRight.isEnabled = false


        lineChart.xAxis.axisMinimum = 1f

        lineChart.animateXY(1000, 1000)
        lineChart.legend.isEnabled = false

        lineChart.setVisibleXRangeMaximum(10f) // Set the maximum number of visible entries

        lineChart.invalidate()
    }

    private inner class DateValueFormatter(private val dates: Array<String>) :
        com.github.mikephil.charting.formatter.ValueFormatter() {

        override fun getFormattedValue(value: Float): String {
            val index = value.toInt()
            return if (index >= 0 && index < dates.size) {
                formatDate(dates[index])
            } else {
                ""
            }
        }

        private fun formatDate(date: String): String {
            val parts = date.split("-")
            if (parts.size == 3) {
                val day = parts[2]
                val month = parts[1]
                val year = parts[0].substring(2)

                return "$day-$month-$year"
            }
            return date
        }
    }
}