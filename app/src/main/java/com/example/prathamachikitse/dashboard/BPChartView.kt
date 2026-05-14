package com.example.prathamachikitse.dashboard

import androidx.compose.runtime.Composable
import androidx.compose.ui.viewinterop.AndroidView
import com.github.mikephil.charting.charts.LineChart
import com.github.mikephil.charting.data.*

@Composable
fun BPChart() {
    AndroidView(factory = { context ->

        val chart = LineChart(context)

        val entries = listOf(
            Entry(1f, 120f),
            Entry(2f, 125f),
            Entry(3f, 130f),
            Entry(4f, 128f),
            Entry(5f, 135f),
            Entry(6f, 132f),
            Entry(7f, 138f)
        )

        val dataSet = LineDataSet(entries, "BP Trend")
        val lineData = LineData(dataSet)

        chart.data = lineData
        chart.invalidate()

        chart
    })
}