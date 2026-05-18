package com.example.prathamachikitse.dashboard

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.AndroidView
import com.example.prathamachikitse.data.MockData
import com.github.mikephil.charting.charts.LineChart
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet

@Composable
fun BPChart() {
    val scrollState = rememberScrollState()
    
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
            .verticalScroll(scrollState)
    ) {
        Text(
            text = "Health Vitals Tracking",
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.primary,
            modifier = Modifier.padding(bottom = 24.dp)
        )

        // Blood Pressure Chart
        Text(
            text = "Blood Pressure (Systolic)",
            fontSize = 18.sp,
            fontWeight = FontWeight.SemiBold,
            modifier = Modifier.padding(bottom = 8.dp)
        )
        
        val bpEntries = if (MockData.patients.isNotEmpty()) {
            MockData.patients[0].bpHistory.mapIndexed { i, d -> Entry(i.toFloat(), d.toFloat()) }
        } else {
            listOf(Entry(0f, 120f), Entry(1f, 125f))
        }

        AndroidView(
            factory = { context ->
                LineChart(context).apply {
                    val dataSet = LineDataSet(bpEntries, "BP Trend").apply {
                        color = android.graphics.Color.parseColor("#EF4444")
                        setCircleColor(android.graphics.Color.parseColor("#EF4444"))
                        lineWidth = 3f
                        setDrawFilled(true)
                        fillColor = android.graphics.Color.parseColor("#EF4444")
                        fillAlpha = 40
                        mode = LineDataSet.Mode.CUBIC_BEZIER
                    }
                    data = LineData(dataSet)
                    description.isEnabled = false
                    xAxis.setDrawGridLines(false)
                    axisRight.isEnabled = false
                    animateX(1000)
                }
            },
            modifier = Modifier
                .fillMaxWidth()
                .height(250.dp)
        )

        Spacer(modifier = Modifier.height(32.dp))

        // Blood Sugar Chart
        Text(
            text = "Blood Sugar Level (mg/dL)",
            fontSize = 18.sp,
            fontWeight = FontWeight.SemiBold,
            modifier = Modifier.padding(bottom = 8.dp)
        )
        
        val sugarEntries = if (MockData.patients.isNotEmpty()) {
            MockData.patients[0].sugarHistory.mapIndexed { i, d -> Entry(i.toFloat(), d.toFloat()) }
        } else {
            listOf(Entry(0f, 140f), Entry(1f, 150f))
        }

        AndroidView(
            factory = { context ->
                LineChart(context).apply {
                    val dataSet = LineDataSet(sugarEntries, "Sugar Level").apply {
                        color = android.graphics.Color.parseColor("#0EA5E9")
                        setCircleColor(android.graphics.Color.parseColor("#0EA5E9"))
                        lineWidth = 3f
                        setDrawFilled(true)
                        fillColor = android.graphics.Color.parseColor("#0EA5E9")
                        fillAlpha = 40
                        mode = LineDataSet.Mode.CUBIC_BEZIER
                    }
                    data = LineData(dataSet)
                    description.isEnabled = false
                    xAxis.setDrawGridLines(false)
                    axisRight.isEnabled = false
                    animateX(1000)
                }
            },
            modifier = Modifier
                .fillMaxWidth()
                .height(250.dp)
        )
        
        Spacer(modifier = Modifier.height(40.dp))
    }
}
