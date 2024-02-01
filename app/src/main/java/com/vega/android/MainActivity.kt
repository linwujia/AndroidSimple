package com.vega.android

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.DividerItemDecoration.VERTICAL
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.vega.android.ceiling.activity.CeilingActivity

class MainActivity : AppCompatActivity() {



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        findViewById<RecyclerView>(R.id.entrance_recycler_view).apply {
            adapter = EntranceAdapter().also {
                it.notifyDataSetChanged(createEntranceDataSet())
            }

            layoutManager = LinearLayoutManager(this@MainActivity)
        }
    }

    private fun createEntranceDataSet(): List<EntranceAdapter.EntranceItem> {
        return arrayListOf<EntranceAdapter.EntranceItem>().apply {
            add(EntranceAdapter.EntranceItem("无事件吸顶RecyclerView", CeilingActivity::class.java))
        }
    }
}